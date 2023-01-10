package com.example.loginexample.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MedicamentRequest {
    @NotBlank
    private String name;

}
