package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.service.FisheringMadeService;
import jakarta.transaction.Transactional;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Base64;
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

        return new ResponseEntity<>(commonResponse, commonResponse.isValid() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/retrieveFisheringRecord/{memberId}")
    public ResponseEntity<List<FisheringMade>> retrieveFisheringRecordByMemberId(@PathVariable("memberId") Long memberId){

        List<FisheringMade> fisheringMadeList = fisheringMadeService.getFisheringMadeByMemberId(memberId);

        fisheringMadeList.forEach(fm -> {
            try {
                int blobLength = (int) fm.getPictureOfFish().length();
                byte[] blobAsBytes = fm.getPictureOfFish().getBytes(1, blobLength);
                String encodedString = Base64.getEncoder().encodeToString(blobAsBytes);

                fm.setPictureOfFishBase64(encodedString);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return new ResponseEntity<>(fisheringMadeList,HttpStatus.OK);
    }

}
