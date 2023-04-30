package com.example.loginexample.serviceTest;

import com.example.loginexample.model.Prescription;
import com.example.loginexample.model.User;
import com.example.loginexample.payload.request.PrescriptionRequest;
import com.example.loginexample.repository.PrescriptionRepository;
import com.example.loginexample.repository.PrescriptionSpecifications;
import com.example.loginexample.service.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;
    @InjectMocks
    private PrescriptionService prescriptionService;

    @BeforeEach
    void initService() {
        prescriptionService = new PrescriptionService(prescriptionRepository);
    }

    @Test
    void getPrescriptionForUsername_WhenGivenValidUsername_ReturnUserPrescription() {
        User user = new User("user", "user@gmail.com", "password");
        Prescription prescription = new Prescription(user, null);

        when(prescriptionRepository.findAll((Specification<Prescription>) any())).thenReturn(List.of(prescription));
        Prescription result = prescriptionService.getPrescriptionForUsername(user.getUsername());
        assertEquals(user.getUsername(), result.getPatient().getUsername());
        assertEquals(prescription.getPrescriptionItems(), result.getPrescriptionItems());
    }

    @Test
    void getPrescriptionForUsername_WhenGivenUsernameWithoutPrescription_ReturnNull() {
        User user = new User("user", "user@gmail.com", "password");

        when(prescriptionRepository.findAll((Specification<Prescription>) any())).thenReturn(Collections.emptyList());
        Prescription result = prescriptionService.getPrescriptionForUsername(user.getUsername());
        assertNull(result);
    }

    @Test
    void savePrescription_WhenGivenValidPrescription_ReturnPrescription() {
        User user = new User("user", "user@gmail.com", "password");
        Prescription prescription = new Prescription(user, null);
        when(prescriptionRepository.save(Mockito.any(Prescription.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Prescription result = prescriptionService.savePrescription(prescription);
        assertEquals(prescription, result);
    }

}
