package com.example.fisherybackend.payloads.request;

import com.example.fisherybackend.entities.HarvestedFishRecords;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TypeOfFishRequest {

    @NotBlank
    @NotNull
    private Long typeOfFishID;

    @NotBlank
    @NotNull
    private HarvestedFishRecords harvestedFishRecords;

    private String typeOfFishName;
    private Boolean isActive;
    private byte[] typeOfFishPicture;

}
