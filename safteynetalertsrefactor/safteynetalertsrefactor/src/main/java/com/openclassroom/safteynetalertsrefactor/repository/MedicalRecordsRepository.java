package com.openclassroom.safteynetalertsrefactor.repository;

import com.openclassroom.safteynetalertsrefactor.model.MedicalRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordsRepository {
    private static final String records = "medicalrecords";
    private final JSONFileReaderRepository JSONFileReaderRepository;

    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    public MedicalRecordsRepository(JSONFileReaderRepository JSONFileReaderRepository) {
        this.JSONFileReaderRepository = JSONFileReaderRepository;
    }

    @PostConstruct
    private void init() {
        List<MedicalRecord> loaded = JSONFileReaderRepository.readList(records, MedicalRecord.class);
        if (loaded != null) {
            medicalRecords.addAll(loaded);
        }
    }

    public List<MedicalRecord> findAll() {
        return new ArrayList<>(medicalRecords);
    }

    public synchronized void add(MedicalRecord newMedicalRecords) {
        medicalRecords.add(0, newMedicalRecords);
        JSONFileReaderRepository.writeList(records, medicalRecords);
    }
}