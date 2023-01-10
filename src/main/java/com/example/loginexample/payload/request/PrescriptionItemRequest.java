package com.example.loginexample.payload.request;

import com.example.loginexample.model.Medicament;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PrescriptionItemRequest {
    private Float quantity;

    private Medicament medicament;

    private Integer daysBetweenAdministrations;

    private LocalDate administrationEndDate;
    private Set<String> timesOfDay;
}
