package com.example.loginexample.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public class PrescriptionRequest {
    @NotBlank
    private String username;
    @NotEmpty
    private Set<PrescriptionItemRequest> prescriptionItems;

}
