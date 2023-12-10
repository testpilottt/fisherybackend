package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.HarvestedFishRecords;
import com.example.fisherybackend.enums.CommonResponseReason;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.service.FisheringMadeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        String data = fisheringMadeRequest.getLocation() + fisheringMadeRequest.getWeightKg() + fisheringMadeRequest.getCountry().name() + fisheringMadeRequest.getMemberId() + fisheringMadeRequest.getTypeOfFishId() + fisheringMadeRequest.getPictureOfFish();
        if (!fisheringMadeRequest.getHash().equals(calculateHash(data))) {
            return new ResponseEntity<>(new CommonResponse("Hash has been tampered.", false, CommonResponseReason.BLOCKCHAIN_TAMPERED), HttpStatus.CONFLICT);
        }

        CommonResponse commonResponse = fisheringMadeService.createFisheringMade(fisheringMadeRequest);

        HttpStatus httpStatus = HttpStatus.CREATED;
        if (CommonResponseReason.COUNTRY_NOTFOUND == commonResponse.getCommonResponseReason()) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (CommonResponseReason.BLOCKCHAIN_TAMPERED == commonResponse.getCommonResponseReason()) {
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(commonResponse, httpStatus);
    }

    @GetMapping("/retrieveFisheringRecord/{memberId}")
    public ResponseEntity<List<HarvestedFishRecords>> retrieveFisheringRecordByMemberId(@PathVariable("memberId") Long memberId){

        List<HarvestedFishRecords> harvestedFishRecordsList = fisheringMadeService.getFisheringMadeByMemberId(memberId);

        harvestedFishRecordsList.forEach(fm -> {
            try {
                int blobLength = (int) fm.getPictureOfFish().length();
                byte[] blobAsBytes = fm.getPictureOfFish().getBytes(1, blobLength);
                String encodedString = Base64.getEncoder().encodeToString(blobAsBytes);
                fm.setPictureOfFishBase64(encodedString);

                blobLength = (int) fm.getTypeOfFish().getTypeOfFishPicture().length();
                blobAsBytes = fm.getTypeOfFish().getTypeOfFishPicture().getBytes(1, blobLength);
                encodedString = Base64.getEncoder().encodeToString(blobAsBytes);

                fm.getTypeOfFish().setTypeOfFishPictureBase64(encodedString);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return new ResponseEntity<>(harvestedFishRecordsList,HttpStatus.OK);
    }

    public String calculateHash(String text) {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        final StringBuilder hexString = new StringBuilder();
        final byte[] bytes = digest.digest(text.getBytes());

        for (final byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
