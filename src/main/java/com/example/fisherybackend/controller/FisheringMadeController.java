package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
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
public class FisheringMadeController {

    @Autowired
    private FisheringMadeService fisheringMadeService;

    @PostMapping("/createFisheringRecord")
    public ResponseEntity<CommonResponse> createFisheringRecord(@RequestBody FisheringMadeRequest fisheringMadeRequest){

        CommonResponse commonResponse = fisheringMadeService.createFisheringMade(fisheringMadeRequest);

        return new ResponseEntity<>(commonResponse,HttpStatus.CREATED);
    }

    @GetMapping("/retrieveFisheringRecord/{memberId}")
    public ResponseEntity<List<FisheringMade>> memberLoginByUsernamePassword(@PathVariable("memberId") Long memberId){

        List<FisheringMade> fisheringMadeList = fisheringMadeService.getFisheringMadeByMemberId(memberId);

        return new ResponseEntity<>(fisheringMadeList,HttpStatus.OK);
    }

}
