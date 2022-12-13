package com.example.loginexample.service;

import com.example.loginexample.model.Medicament;
import com.example.loginexample.payload.request.MedicamentRequest;
import com.example.loginexample.repository.MedicamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicamentService {

    private MedicamentRepository medicamentRepository;

    public Medicament addMedicament(MedicamentRequest request) {
        return medicamentRepository.save(new Medicament(request.getName()));
    }
    public Medicament getMedicamentByName(String name) {
        return medicamentRepository.findByName(name);
    }

    public List<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }
}
