package com.example.fisherybackend.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Blob;

@Getter
public class MembersRequest {

    @NotBlank
    @NotNull
    private Long userId;

    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private Long hoursLogged;
    private Blob profilePicture;

}
