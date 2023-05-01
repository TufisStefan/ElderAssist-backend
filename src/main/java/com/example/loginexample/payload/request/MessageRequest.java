package com.example.loginexample.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class MessageRequest {
    @NotBlank
    private String content;
    @NotBlank
    private String phoneNumber;
}
