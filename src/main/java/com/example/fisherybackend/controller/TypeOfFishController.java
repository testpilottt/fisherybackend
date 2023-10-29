package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.payloads.request.TypeOfFishRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.service.TypeOfFishService;
import jakarta.transaction.Transactional;
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
public class TypeOfFishController {

    @Autowired
    private TypeOfFishService typeOfFishService;

    @PostMapping("/createOrUpdateTypeOfFish")
    public ResponseEntity<CommonResponse> createOrUpdateTypeOfFish(@RequestBody TypeOfFishRequest typeOfFishRequest){

        CommonResponse commonResponse = typeOfFishService.createOrUpdateTypeOfFish(typeOfFishRequest);

        return new ResponseEntity<>(commonResponse, commonResponse.isValid() ? HttpStatus.CREATED : HttpStatus.ALREADY_REPORTED);
    }

    @PostMapping("/setActiveStatusOfTypeOfFish")
    public ResponseEntity<CommonResponse> setActiveStatusOfTypeOfFish(@RequestBody TypeOfFishRequest typeOfFishRequest){

        CommonResponse commonResponse = typeOfFishService.setActiveStatusOfTypeOfFish(typeOfFishRequest.getTypeOfFishID(), typeOfFishRequest.getIsActive());

        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }
    @GetMapping("/retrieveTypeOfFish")
    public ResponseEntity<List<TypeOfFish>> retrieveTypeOfFish(){
        List<TypeOfFish> typeOfFishList = typeOfFishService.getAllTypeOfFish();

        typeOfFishList.forEach(tof -> {
            try {
                int blobLength = (int) tof.getTypeOfFishPicture().length();
                byte[] blobAsBytes = tof.getTypeOfFishPicture().getBytes(1, blobLength);
                String encodedString = Base64.getEncoder().encodeToString(blobAsBytes);

                tof.setTypeOfFishPictureBase64(encodedString);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return new ResponseEntity<>(typeOfFishList, HttpStatus.OK);
    }
}
