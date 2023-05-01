package com.example.loginexample.controller;

import com.example.loginexample.payload.request.MessageRequest;
import com.example.loginexample.utils.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/message")
public class MessageController {

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage( @RequestBody MessageRequest messageRequest) {
        try {
            MessageSender.sendMessage(messageRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
