package com.bookstore.bookstoreapi.order.controller;

import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.order.dto.BuyerInfoDTO;
import com.bookstore.bookstoreapi.order.dto.ImportDTO;
import com.bookstore.bookstoreapi.order.service.OrderService;
import com.bookstore.bookstoreapi.order.service.PaymentsService;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.KeepAliveCache;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


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


             return postRequest(auth, importDTO);
        }catch (Exception e){
            log.debug("Exception : "+ e);
        }
        return null;
    }


    private ApiResponse<String> postRequest(String auth, ImportDTO importDTO){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Authorization",auth);

       // headers.setContentType(MediaType.APPLICATION_JSON);
       // headers.add("Authorization",auth);

        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = rt.postForEntity(API_URL+"/payments/"+importDTO.getImp_uid(), entity, String.class);
            log.debug("rsp : " + response);

            compareData(response, importDTO);
            return new ApiResponse<>(true,"대충 성공");

        }catch (Exception e){
            log.debug("Exception : "+ e);
            return new ApiResponse<>(false,"결제 정보를 받아오는데 실패했습니다");
        }

    }


    private void compareData(ResponseEntity<String> response, ImportDTO importDTO)throws Exception {

        JSONObject rspBody = new JSONObject(response.getBody());
        JSONObject Data = new JSONObject(rspBody.get("response").toString());
        int amount = Integer.parseInt(Data.get("amount").toString());

        //결제 정보 비교
        int amountToBePaid = orderService.getTotalAmount(importDTO.getMerchant_uid());

        if(amountToBePaid == amount){
            // Json object dto로 가공 찾아보기
            paymentsService.setPaymentsInfo(Data);  //결제정보 저장

        } else { // 결제금액 불일치. 위/변조 된 결제
            log.debug("위조된 결제");
        }
    }
}
//rspData.get("amount")