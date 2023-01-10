package com.example.loginexample.repository;

import com.example.loginexample.model.ETimeOfDay;
import com.example.loginexample.model.TimeOfDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOfDayRepository extends JpaRepository<TimeOfDay, Long> {

    TimeOfDay findByName(ETimeOfDay name);
}
