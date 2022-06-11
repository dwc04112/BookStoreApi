package com.bookstore.bookstoreapi.order.controller;

import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.order.dto.ImportDTO;
import com.bookstore.bookstoreapi.order.dto.PaymentsDTO;
import com.bookstore.bookstoreapi.order.dto.PaymentsRsp;
import com.bookstore.bookstoreapi.order.model.Payments;
import com.bookstore.bookstoreapi.order.service.OrderService;
import com.bookstore.bookstoreapi.order.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/payments")
public class PaymentsController {
    private final OrderService orderService;
    private final PaymentsService paymentsService;

    private static final String API_URL = "https://api.iamport.kr";
    private static final String api_key = "9482270387652782";
    private static final String api_secret = "c022f4a691f9cdaa2bc54885bb730eabb710ccb19d8b27360ab029c946b684ad173b45ddccda3233";
    RestTemplate rt = new RestTemplate();


    @ResponseBody
    @PostMapping("/complete")
    public ApiResponse<String> getOrderInfo(@RequestBody ImportDTO importDTO)throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        JSONObject body= new JSONObject();
        body.put("imp_key", api_key);
        body.put("imp_secret", api_secret);

        try {
            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            ResponseEntity<String> response = rt.postForEntity(API_URL+"/users/getToken", entity, String.class);
            log.debug("rsp : " + response);

            // No HttpMessageConverter "application/json" 에러때문에
            //json -> String 변환해서 호출. 받은 String 값 다시 json 으로 변환
            JSONObject rspBody = new JSONObject(response.getBody());
            JSONObject rspData = new JSONObject(rspBody.get("response").toString());
            String auth = rspData.get("access_token").toString();


            ApiResponse<PaymentsDTO> getPayments = postRequest(auth, importDTO);
            assert getPayments != null;

            if(!getPayments.isSuccess()){
                 return new ApiResponse<>(false, "결제 정보 불러오기를 실패했습니다. 에러코드 "+ getPayments.getData().getCode());
            }else{
                // 결제정보 불러오기에 성공하면
                return compareData( getPayments.getData().getResponse(), importDTO);
            }


        }catch (Exception e){
            log.debug("Exception (getOrderInfo) : "+ e);
        }
        return null;
    }

    private ApiResponse<PaymentsDTO> postRequest(String auth, ImportDTO importDTO){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Authorization",auth);

        try {
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
            Payments saveData = paymentsService.setPaymentsInfo(paymentsRsp);           //결제정보 저장
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
}
//rspData.get("amount")