package com.bookstore.bookstoreapi.payments.service;


import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.payments.dto.ImportDTO;
import com.bookstore.bookstoreapi.payments.dto.PaymentsDTO;
import com.bookstore.bookstoreapi.payments.dto.PaymentsRsp;
import com.bookstore.bookstoreapi.payments.model.AccessToken;
import com.bookstore.bookstoreapi.payments.model.Payments;
import com.bookstore.bookstoreapi.payments.model.PaymentsRepository;

import com.bookstore.bookstoreapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentsService {
    final OrderService orderService;
    final PaymentsRepository paymentsRepository;

    final String API_URL = "https://api.iamport.kr";
    final String api_key = "9482270387652782";
    final String api_secret = "c022f4a691f9cdaa2bc54885bb730eabb710ccb19d8b27360ab029c946b684ad173b45ddccda3233";
    RestTemplate rt = new RestTemplate();


    public ApiResponse<String> getOrderInfo(ImportDTO importDTO) {
        try {
            ApiResponse<PaymentsDTO> payments = getPayments(getToken(), importDTO);
            if(payments==null){
                throw new Exception();
            }

            if(!payments.isSuccess()){
                return new ApiResponse<>(false, "결제 정보 불러오기를 실패했습니다. 에러코드 "+ payments.getData().getCode());
            }else{
                // 결제정보 불러오기에 성공하면
                return compareData( payments.getData().getResponse(), importDTO);
            }
        }catch (Exception e){
            log.debug("Exception (getOrderInfo) : "+ e);
        }
        return null;
    }

    //토큰 받아오기
    private AccessToken getAuth() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "application/json");

            JSONObject body = new JSONObject();
            body.put("imp_key", this.api_key);
            body.put("imp_secret", api_secret);

            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            ResponseEntity<String> response = rt.postForEntity(API_URL + "/users/getToken", entity, String.class);

            if(response.getStatusCodeValue()!=200){
                throw new RuntimeException("Failed get Auth : HTTP error code : "
                        + response.getStatusCodeValue());
            }

            // No HttpMessageConverter "application/json" 에러때문에
            //json -> String 변환해서 호출. 받은 String 값 다시 json 으로 변환
            JSONObject rspBody = new JSONObject(response.getBody());
            JSONObject rspData = new JSONObject(rspBody.get("response").toString());

            AccessToken auth = new AccessToken();
            auth.setToken(rspData.get("access_token").toString());
            auth.setNow((Integer) rspData.get("now"));
            auth.setExpired_at((Integer) rspData.get("expired_at"));

            return auth;

        }catch (Exception e){
            log.debug("Exception (getAuth) : "+ e);
        }
        return null;
    }


    public String getToken()  {
        int nowUnixTime = (int) System.currentTimeMillis() / 1000;

        AccessToken auth = new AccessToken();

        //시간 지났으면 재발급
        if(auth.getExpired_at() <  nowUnixTime){
            return Objects.requireNonNull(getAuth()).getToken();
        }else{
            return auth.getToken();
        }
    }


    // Post Request
    private ApiResponse<PaymentsDTO> getPayments(String auth, ImportDTO importDTO){

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Authorization",auth);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<PaymentsDTO> response = rt.postForEntity(API_URL+"/payments/"+importDTO.getImp_uid(), entity, PaymentsDTO.class);
            log.debug("post R : " + response.getStatusCodeValue());

            if(response.getStatusCodeValue() == 200){
                return new ApiResponse<>(true, response.getBody() );
            }else{
                return new ApiResponse<>(false, response.getBody() );
            }

        }catch (Exception e){
            log.debug("Exception (postRequest) : "+ e);
        }
        return null;
    }


    private ApiResponse<String> compareData(PaymentsRsp paymentsRsp, ImportDTO importDTO) {
        //결제 정보 비교
        int amountToBePaid = orderService.getTotalAmount(importDTO.getMerchant_uid());
        log.debug("compare : " + paymentsRsp.getAmount() + " and " + amountToBePaid);

        if(amountToBePaid == paymentsRsp.getAmount()){
            Payments saveData = setPaymentsInfo(paymentsRsp);           //결제정보 저장
            boolean orderUpdate = orderService.updateState(saveData.getOrderId(), saveData.getPayStatus());   //order 상태 업데이트
            if(!orderUpdate){
                return new ApiResponse<>(false, "결제 후, 주문상태 업데이트를 실패했습니다." );
            }
            switch (saveData.getPayStatus()){
                case ("ready") : {
                    return new ApiResponse<>(true, "가상계좌 발급 부분" );
                    //추가예정
                }
                case ("paid") : {
                    return new ApiResponse<>(true, "일반 결제에 성공했습니다." );
                }
            }
        } else { // 결제금액 불일치. 위/변조 된 결제
            return new ApiResponse<>(false, "위조된 결제 시도." );
        }
        return null;
    }


    // save payments
    public Payments setPaymentsInfo(PaymentsRsp data) {
        Payments payments = Payments.builder()
                .paymentId(data.getImp_uid())
                .orderId(data.getMerchant_uid())
                .payMethod(data.getPay_method())
                .pgProvide(data.getPg_provider())
                .paidAt(data.getPaid_at())
                .payStatus(data.getStatus())
                .payAmount(data.getAmount())
                .cancelAmount(0)
                .buyerName(data.getBuyer_name())
                .bankName(data.getVbank_name())
                .bankHorder(data.getVbank_holder())
                .build();
        return paymentsRepository.save(payments);
    }

    //환불부분
    public ApiResponse<String> cancelPay(ImportDTO importDTO)throws Exception {
        Optional<Payments> payments = this.paymentsRepository.findPaymentsByOrderId(importDTO.getMerchant_uid());
        Payments data = payments.orElseThrow(() -> new RuntimeException("no data : find payments by order_id"));

        int cancelableAmount = data.getPayAmount() - data.getCancelAmount();
        if (cancelableAmount <= 0) { // 이미 전액 환불된 경우
            return new ApiResponse<>(false,"이미 전액환불된 주문입니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", getToken());

        JSONObject body= new JSONObject();
        body.put("imp_uid", data.getPaymentId());
        body.put("amount", data.getPayAmount());    //전액환불
        body.put("checksum", cancelableAmount);

        try {
            HttpEntity<String> entity = new HttpEntity<>(body.toString() ,headers);
            ResponseEntity<PaymentsDTO> cancel = rt.postForEntity(API_URL+"/payments/cancel", entity, PaymentsDTO.class);
            log.debug("cancel R : " +cancel);

            //여기부터 수정? payments 업데이트 완료 order상태 업데이트 해야함
            data.updateCancel("cancel" , Objects.requireNonNull(cancel.getBody()).getResponse().getCancel_amount());
            paymentsRepository.save(data);

        }catch (Exception e){
            log.debug("Exception (cancelPay) : "+ e);
        }
        return null;
    }

}
