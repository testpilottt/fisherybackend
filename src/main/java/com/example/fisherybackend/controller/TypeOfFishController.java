package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.request.TypeOfFishRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.service.MembersService;
import com.example.fisherybackend.service.TypeOfFishService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Transactional
@RestController
@RequestMapping("/fishery")
public class TypeOfFishController {

    @Autowired
    private TypeOfFishService typeOfFishService;

    @PostMapping("/createTypeOfFish")
    public ResponseEntity<CommonResponse> createTypeOfFish(@RequestBody TypeOfFishRequest typeOfFishRequest){

        CommonResponse commonResponse = typeOfFishService.createTypeOfFish(typeOfFishRequest);

        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }

    @PostMapping("/setActiveStatusOfTypeOfFish")
    public ResponseEntity<CommonResponse> setActiveStatusOfTypeOfFish(@RequestBody TypeOfFishRequest typeOfFishRequest){

        CommonResponse commonResponse = typeOfFishService.setActiveStatusOfTypeOfFish(typeOfFishRequest.getTypeOfFishID(), typeOfFishRequest.getIsActive());

        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }

}
