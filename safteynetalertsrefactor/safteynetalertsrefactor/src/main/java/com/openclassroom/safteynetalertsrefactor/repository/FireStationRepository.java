package com.openclassroom.safteynetalertsrefactor.repository;

import com.openclassroom.safteynetalertsrefactor.model.FireStation;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FireStationRepository {
    private static final String station = "firestations";
    private final JSONFileReaderRepository JSONFileReaderRepository;

    private final List<FireStation> firestations = new ArrayList<>();

    public FireStationRepository(JSONFileReaderRepository JSONFileReaderRepository) {
        this.JSONFileReaderRepository = JSONFileReaderRepository;
    }

    @PostConstruct
    private void init() {
        List<FireStation> loaded = JSONFileReaderRepository.readList(station, FireStation.class);
        if (loaded != null) {
            firestations.addAll(loaded);
        }
    }

    public List<FireStation> findAll() {
        return new ArrayList<>(firestations);
    }

    public synchronized void add(FireStation newFireStation) {
        firestations.add(0, newFireStation);
        JSONFileReaderRepository.writeList(station, firestations);
    }
}