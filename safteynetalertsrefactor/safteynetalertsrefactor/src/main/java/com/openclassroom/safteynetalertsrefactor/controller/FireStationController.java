package com.openclassroom.safteynetalertsrefactor.controller;
import com.openclassroom.safteynetalertsrefactor.model.FireStation;
import com.openclassroom.safteynetalertsrefactor.service.FireStationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestations")
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    public List<FireStation> getAllFireStations() {
        return fireStationService.getAllFireStations();
    }

    @PostMapping
    public FireStation addFireStation(@RequestBody FireStation fireStation){
        return fireStationService.addFireStation(fireStation);
    }

}