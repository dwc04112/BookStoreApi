package com.bookstore.bookstoreapi.payments.service;


import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.order.model.Orders;
import com.bookstore.bookstoreapi.payments.dto.*;
import com.bookstore.bookstoreapi.payments.model.AccessToken;
import com.bookstore.bookstoreapi.payments.model.Payments;
import com.bookstore.bookstoreapi.payments.model.PaymentsRepository;

import com.bookstore.bookstoreapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentsService {
    final OrderService orderService;
    final PaymentsRepository paymentsRepository;
    final MemberRepository memberRepository;

    final String API_URL = "https://api.iamport.kr";
    final String api_key = "9482270387652782";
    final String api_secret = "c022f4a691f9cdaa2bc54885bb730eabb710ccb19d8b27360ab029c946b684ad173b45ddccda3233";
    RestTemplate rt = new RestTemplate();



    // Post Request
    private ApiResponse<PaymentsDTO> postRequest(String auth,String url, JSONObject body){

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Authorization",auth);

            // body 있을때 없을때
            HttpEntity<String> entity = new HttpEntity<>(headers);
            if(body!=null){
                entity = new HttpEntity<>(body.toString(), headers);
            }
            ResponseEntity<PaymentsDTO> response = rt.postForEntity(API_URL+url, entity, PaymentsDTO.class);
            log.debug("post Rsp : " + response);

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

    //Get Request
    private ApiResponse<PaymentsDTO> getRequest(String auth,String url){

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",auth);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            log.debug(url);
            ResponseEntity<PaymentsDTO> response = rt.exchange(API_URL+url, HttpMethod.GET, entity, PaymentsDTO.class);
            log.debug("get Rsp : " + response);

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


    // 토큰 받아오기
    private AccessToken getAuth() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "application/json");

            JSONObject body = new JSONObject();
            body.put("imp_key", this.api_key);
            body.put("imp_secret", api_secret);

            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            ResponseEntity<GetTokenDTO> response = rt.postForEntity(API_URL + "/users/getToken", entity, GetTokenDTO.class);

            if(response.getStatusCodeValue()!=200){
                throw new RuntimeException("Failed get Auth : HTTP error code : "
                        + response.getStatusCodeValue());
            }

            return Objects.requireNonNull(response.getBody()).getResponse();



        }catch (Exception e){
            log.debug("Exception (getAuth) : "+ e);
        }
        return null;
    }


    // 토큰 사용
    public String getToken()  {
        int nowUnixTime = (int) System.currentTimeMillis() / 1000;

        AccessToken auth = new AccessToken();

        //토큰이 없으면?
        if(auth.getToken() == null){
            return Objects.requireNonNull(getAuth()).getToken();
        }

        //시간 지났으면 재발급
        if(auth.getExpired_at() <  nowUnixTime){
            return Objects.requireNonNull(getAuth()).getToken();
        }else{
            return auth.getToken();
        }
    }

    //결제부분
    public ApiResponse<String> getOrderInfo(ImportDTO importDTO) {

        try {
            ApiResponse<PaymentsDTO> response = postRequest(getToken(), "/payments/"+importDTO.getImp_uid(), null);
            if(response==null){
                throw new Exception();
            }
            if(!response.isSuccess()){
                return new ApiResponse<>(false, "결제 정보 불러오기를 실패했습니다. 에러코드 "+ response.getData().getCode());
            }else{
                // 결제정보 불러오기에 성공하면 결제정보 비교
                return compareData( response.getData().getResponse(), importDTO);
            }
        }catch (Exception e){
            log.debug("Exception (getOrderInfo) : "+ e);
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
                    return new ApiResponse<>(true, "일반결제 완료." );
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

        JSONObject body= new JSONObject();
        body.put("imp_uid", data.getPaymentId());
        body.put("amount", data.getPayAmount());    //전액환불
        body.put("checksum", cancelableAmount);

        ApiResponse<PaymentsDTO> response = postRequest(getToken(),"/payments/cancel", body);
        if(response==null){
            throw new Exception();
        }
        if(!response.isSuccess()){
            return new ApiResponse<>(false, "결제취소 정보 불러오기를 실패했습니다. 에러코드 "+ response.getData().getCode());
        }else{
            if(cancelUpdate(data, response)){
                return new ApiResponse<>(true, "결제취소 완료");
            }else{
                return new ApiResponse<>(false, "결제취소 후, 주문상태 업데이트를 실패했습니다." );
            }
        }
    }

    @Transactional
    public boolean cancelUpdate(Payments data, ApiResponse<PaymentsDTO> response)throws Exception {
        // 결제정보 불러오기에 성공하면
        data.updateCancel("cancel", response.getData().getResponse().getCancel_amount());
        paymentsRepository.save(data);
        boolean result = orderService.updateState(data.getOrderId(), data.getPayStatus());
        return Objects.equals(data.getPayStatus(), "cancel") && result;
    }

    // 결제내역 가져오기
    public ApiResponse<PayAndOrderItems> getPaymentById(Long orderId) throws Exception {
        Orders orders = orderService.getOrderByPayments(orderId);
        if(orders == null){
            return new ApiResponse<>(false, "결제한 책 정보가 없습니다.");
        }

        Payments payments = paymentsRepository.getPaymentsByOrderId(orderId);
        if(payments==null){
            PayAndOrderItems payAndOrderItems = new PayAndOrderItems();
            payAndOrderItems.setItems(orders);
            return new ApiResponse<>(true,"결제 중단한 요청",payAndOrderItems);
        }

        PayAndOrderItems payAndOrderItems = new PayAndOrderItems();
        payAndOrderItems.setPayments(payments);
        payAndOrderItems.setItems(orders);
        return new ApiResponse<>(true,payAndOrderItems);
    }
    //휴대폰 인증 > 다날 유료결제시 사용가능
    public void phoneCertification(String imp_uid) {
        log.debug("certification  : " + imp_uid);
        ApiResponse<PaymentsDTO> response = getRequest(getToken(), "/certification/"+imp_uid);
    }
}
