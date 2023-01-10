package com.example.loginexample.service;

import com.example.loginexample.model.Prescription;
import com.example.loginexample.repository.PrescriptionItemRepository;
import com.example.loginexample.repository.PrescriptionRepository;
import com.example.loginexample.repository.PrescriptionSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrescriptionService {
    private PrescriptionRepository prescriptionRepository;

    private PrescriptionItemRepository prescriptionItemRepository;

    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionForUsername(String username) {
        List<Prescription> prescriptions = prescriptionRepository
                .findAll(PrescriptionSpecifications.hasPatientWithUsername(username));
        return prescriptions.size() > 0 ? prescriptions.get(0) : null;
    }
}