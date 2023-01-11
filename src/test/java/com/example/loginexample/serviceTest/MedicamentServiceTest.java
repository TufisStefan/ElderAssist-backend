package com.example.loginexample.serviceTest;

import com.example.loginexample.model.Medicament;
import com.example.loginexample.payload.request.MedicamentRequest;
import com.example.loginexample.repository.MedicamentRepository;
import com.example.loginexample.service.MedicamentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicamentServiceTest {
    @Mock
    private MedicamentRepository medicamentRepository;
    @InjectMocks
    private MedicamentService medicamentService;

    @BeforeEach
    void initService() {
        medicamentService = new MedicamentService(medicamentRepository);
    }

    @Test
    void getAllMedicaments_WhenCalled_ReturnMedicaments() {
        Medicament medicament = new Medicament();
        when(medicamentRepository.findAll()).thenReturn(List.of(medicament));
        List<Medicament> result = medicamentService.getAllMedicaments();
        assertEquals(1, result.size());
    }

    @Test
    void addMedicament_WhenGivenValidMedicament_ReturnMedicament() {
        MedicamentRequest medicament = new MedicamentRequest("Med");
        when(medicamentRepository.save(Mockito.any(Medicament.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Medicament result = medicamentService.addMedicament(medicament);
        assertEquals(medicament.getName(), result.getName());
    }

    @Test
    void getMedicamentByName_WhenGivenValidName_ReturnMedicament() {
        Medicament medicament = new Medicament("Med");
        when(medicamentRepository.findByName(any(String.class))).thenAnswer(i -> new Medicament((String) i.getArguments()[0]));
        Medicament result = medicamentService.getMedicamentByName(medicament.getName());
        assertEquals(medicament.getName(), result.getName());
    }
}
