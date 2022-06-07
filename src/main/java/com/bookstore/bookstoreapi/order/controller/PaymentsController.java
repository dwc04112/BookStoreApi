package com.bookstore.bookstoreapi.order.controller;

import com.bookstore.bookstoreapi.order.dto.BuyerInfoDTO;
import com.bookstore.bookstoreapi.order.dto.ImportDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/payments")
public class PaymentsController {

    private static final String API_URL = "https://api.iamport.kr";
    private static final String api_key = "9482270387652782";
    private static final String api_secret = "c022f4a691f9cdaa2bc54885bb730eabb710ccb19d8b27360ab029c946b684ad173b45ddccda3233";
    RestTemplate rt = new RestTemplate();

    @ResponseBody
    @PostMapping("/complete")
    public void getOrderInfo(@RequestBody ImportDTO importDTO) throws Exception{


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        JSONObject jsonObject=new JSONObject();
        jsonObject.put("imp_key", api_key);
        jsonObject.put("imp_secret", api_secret);


        try {
            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
            ResponseEntity<String> response = rt.postForEntity(API_URL+"/users/getToken", entity, String.class);
            log.debug("rsp : " + response);


            // No HttpMessageConverter "application/json" 에러때문에
            //json -> String 변환해서 호출. 받은 String 값 다시 json 으로 변환
            JSONObject rspBody = new JSONObject(response.getBody());
            JSONObject rspData = new JSONObject(rspBody.get("response").toString());
            String auth = rspData.get("access_token").toString();


            String postRequestData = postRequest(auth, importDTO.getImp_uid());

        }catch (Exception e){
            log.debug("Exception : "+ e);
        }
    }

    private String postRequest(String auth, String imp_uid){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",auth);

        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = rt.postForEntity(API_URL+"/payments/"+imp_uid, entity, String.class);
            log.debug("rsp : " + response);

            JSONObject rspBody = new JSONObject(response.getBody());
            JSONObject rspData = new JSONObject(rspBody.get("response").toString());
            log.debug("rsp : " + rspData.get("amount"));

        }catch (Exception e){
            log.debug("Exception : "+ e);
        }

        return null;
    }
}
