package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.CountrySetting;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.CountrySettingRepository;
import com.example.fisherybackend.service.FisheringMadeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/fishery")
public class CountrySettingController {
    @Autowired
    private CountrySettingRepository countrySettingRepository;

    @GetMapping("/getCountrySettings")
    public ResponseEntity<List<CountrySetting>> getCountrySettings() {
        List<CountrySetting> countrySettings = countrySettingRepository.findAll();
        return new ResponseEntity<>(countrySettings, HttpStatus.CREATED);
    }
}
