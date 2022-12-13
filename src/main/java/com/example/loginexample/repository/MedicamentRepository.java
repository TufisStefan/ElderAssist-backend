package com.example.loginexample.repository;

import com.example.loginexample.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    Medicament findByName(String name);
}
