package com.example.loginexample.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class MedicamentRequest {
    @NotBlank
    private String name;

}
