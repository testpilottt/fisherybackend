package com.example.fisherybackend.payloads.request;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.enums.Region;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
public class FisheringMadeRequest {

    private Long fisheringId;

    private Members members;

    private Long memberId;

    private String location;

    private String pictureOfFish;

    private Double weightKg;

    private Country country;

    private Region region;

    private TypeOfFish typeOfFish;

    private Long typeOfFishId;

    private String hash;
}
