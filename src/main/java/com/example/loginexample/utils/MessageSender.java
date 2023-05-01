package com.example.loginexample.utils;

import com.example.loginexample.payload.request.MessageRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class MessageSender {
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_ACCOUNT_TOKEN");

    public static void sendMessage(MessageRequest messageRequest) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber(messageRequest.getPhoneNumber()),
                        new PhoneNumber("+16206590459"),
                        messageRequest.getContent())
                .create();

    }
}
