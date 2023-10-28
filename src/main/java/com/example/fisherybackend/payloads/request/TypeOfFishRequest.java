package com.example.fisherybackend.payloads.request;

import com.example.fisherybackend.entities.FisheringMade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Blob;

@Getter
public class TypeOfFishRequest {

    @NotBlank
    @NotNull
    private Long typeOfFishID;

    @NotBlank
    @NotNull
    private FisheringMade fisheringMade;

    private String typeOfFishName;
    private Boolean isActive;
    private byte[] typeOfFishPicture;

}
