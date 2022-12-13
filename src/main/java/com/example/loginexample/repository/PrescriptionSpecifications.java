package com.example.loginexample.repository;

import com.example.loginexample.model.Prescription;
import com.example.loginexample.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class PrescriptionSpecifications {
    public static Specification<Prescription> hasPatientWithUsername(String patientName) {
        return ((root, query, criteriaBuilder) -> {
            Join<User, Prescription> patientPrescription = root.join("patient");
            return criteriaBuilder.equal(patientPrescription.get("username"), patientName);
        });
    }
}
