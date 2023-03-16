package com.example.fisherybackend.payloads.request;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.enums.Region;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.sql.Blob;

@Getter

public class FisheringMadeRequest {

    private Long fisheringId;

    private Members members;

    private Long memberID;

    private String location;

    private Blob pictureOfFish;

    private Integer weightKg;

    private Country country;

    private Region region;

    private TypeOfFish typeOfFish;

    //blockchain
    private String fisheringHash;
}
