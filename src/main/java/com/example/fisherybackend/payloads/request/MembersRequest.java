package com.example.fisherybackend.payloads.request;

import com.example.fisherybackend.entities.HarvestedFishRecords;
import com.example.fisherybackend.enums.AccessLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Blob;
import java.util.List;

@Getter
public class MembersRequest {

    @NotBlank
    @NotNull
    private Long userId;

    private List<HarvestedFishRecords> harvestedFishRecords;
    private String username;
    private String firstName;

    private String lastName;
    private String password;
    private AccessLevel accessLevel;
    private Long hoursLogged;
    private byte[] profilePicture;

    //blockchain
    private String membersHash;

}
