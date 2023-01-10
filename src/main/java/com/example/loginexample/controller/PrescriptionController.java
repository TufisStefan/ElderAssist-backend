package com.example.loginexample.controller;

import com.example.loginexample.model.*;
import com.example.loginexample.payload.request.MedicamentRequest;
import com.example.loginexample.payload.request.PrescriptionRequest;
import com.example.loginexample.repository.TimeOfDayRepository;
import com.example.loginexample.service.MedicamentService;
import com.example.loginexample.service.PrescriptionService;
import com.example.loginexample.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/prescription")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PrescriptionController {
    private MedicamentService medicamentService;
    private PrescriptionService prescriptionService;
    private UserService userService;
    private TimeOfDayRepository timeOfDayRepository;

    @PostMapping("/medicament/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMedicament(@Valid @RequestBody MedicamentRequest medicamentRequest) {
        try {
            Medicament medicament = medicamentService.addMedicament(medicamentRequest);
            return ResponseEntity.ok(medicament);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicament already existent in database.");
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addPrescription(@Valid @RequestBody PrescriptionRequest prescriptionRequest) {
        Set<PrescriptionItem> prescriptionItems = new HashSet<>();
        User patient = userService.findUserByName(prescriptionRequest.getUsername());
        prescriptionRequest.getPrescriptionItems().forEach(item -> {
            Medicament med = medicamentService.getMedicamentByName(item.getMedicament().getName());
            PrescriptionItem prescriptionItem = new PrescriptionItem(med, item.getQuantity(), item.getAdministrationEndDate(), item.getDaysBetweenAdministrations());
            Set<String> timesOfDayStr = item.getTimesOfDay();
            Set<TimeOfDay> timesOfDay = new HashSet<>();
            timesOfDayStr.forEach(timeOfDay -> {
                switch (timeOfDay) {
                    case "MORNING":
                        TimeOfDay morningTime = timeOfDayRepository.findByName(ETimeOfDay.MORNING);
                        timesOfDay.add(morningTime);
                        break;
                    case "NOON":
                        TimeOfDay noonTime = timeOfDayRepository.findByName(ETimeOfDay.NOON);
                        timesOfDay.add(noonTime);
                        break;
                    case "EVENING":
                        TimeOfDay eveningTime = timeOfDayRepository.findByName(ETimeOfDay.EVENING);
                        timesOfDay.add(eveningTime);
                        break;
                }
            });
            prescriptionItem.setTimesOfDay(timesOfDay);
            prescriptionItems.add(prescriptionItem);
        });
        Prescription prescription = new Prescription(patient, prescriptionItems);
        return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.savePrescription(prescription));
    }

    @GetMapping("/medicament/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMedicaments() {
        return ResponseEntity.status(HttpStatus.OK).body(medicamentService.getAllMedicaments());
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getPrescriptionForPatient(@RequestParam(name = "username") String username) {
        Prescription prescription = prescriptionService.getPrescriptionForUsername(username);
        return prescription != null
                ? ResponseEntity.status(HttpStatus.OK).body(prescription)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No prescription assigned.");
    }
}
