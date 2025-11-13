package com.openclassroom.safteynetalertsrefactor.controller;
import com.openclassroom.safteynetalertsrefactor.model.FireStation;
import com.openclassroom.safteynetalertsrefactor.model.MedicalRecord;
import com.openclassroom.safteynetalertsrefactor.service.FireStationService;
import com.openclassroom.safteynetalertsrefactor.service.MedicalRecordsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecords")
public class MedicalRecordController {
    private final MedicalRecordsService medicalRecordsService;

    public MedicalRecordController(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordsService.getAllMedicalRecords();
    }

    @PostMapping
    public MedicalRecord addMedicalRecords(@RequestBody MedicalRecord medicalRecord){
        return medicalRecordsService.addMedicalRecords(medicalRecord);
    }
}