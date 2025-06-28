package com.example.notificationservice.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toRawPhoneNumber, String messageText) {
        String toPhoneNumber = toRawPhoneNumber.replaceAll("\\s+", "");
        if (toPhoneNumber.startsWith("0")) {
            toPhoneNumber = "+33" + toPhoneNumber.substring(1);
        }

        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageText
        ).create();

        System.out.println("✅ SMS envoyé à " + toPhoneNumber + " avec SID: " + message.getSid());
    }
}

