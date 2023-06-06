package com.shoppingportal.user.service;

import com.shoppingportal.user.model.MailContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailService {

    @Autowired
    RestTemplate restTemplate;

    public void triggerMail(MailContent mailContent){
        HttpEntity httpEntity = new HttpEntity<>(mailContent);

        ResponseEntity<String> resp =
                restTemplate.exchange("http://localhost:8084/sendEmail", HttpMethod.POST,httpEntity, String.class);
        System.out.println(resp.getBody());

    }
}
