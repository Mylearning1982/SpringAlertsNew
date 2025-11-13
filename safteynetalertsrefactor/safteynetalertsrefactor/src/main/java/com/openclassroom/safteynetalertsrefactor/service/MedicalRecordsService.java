package com.openclassroom.safteynetalertsrefactor.service;

import com.openclassroom.safteynetalertsrefactor.model.FireStation;
import com.openclassroom.safteynetalertsrefactor.model.MedicalRecord;
import com.openclassroom.safteynetalertsrefactor.repository.FireStationRepository;
import com.openclassroom.safteynetalertsrefactor.repository.MedicalRecordsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsService {
    private  final MedicalRecordsRepository medicalRecordsRepository;

    public MedicalRecordsService(MedicalRecordsRepository medicalRecordsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordsRepository.findAll();
    }

    public MedicalRecord addMedicalRecords(MedicalRecord medicalRecord){
        medicalRecordsRepository.add(medicalRecord);
        return medicalRecord;
    }
}