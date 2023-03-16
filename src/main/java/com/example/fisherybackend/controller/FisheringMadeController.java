package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.service.FisheringMadeService;
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
public class FisheringMadeController {

    @Autowired
    private FisheringMadeService fisheringMadeService;

    @PostMapping("/createFisheringRecord")
    public ResponseEntity<CommonResponse> createFisheringRecord(@RequestBody FisheringMadeRequest fisheringMadeRequest){

        CommonResponse commonResponse = fisheringMadeService.createFisheringMade(fisheringMadeRequest);

        return new ResponseEntity<>(commonResponse,HttpStatus.CREATED);
    }

}
