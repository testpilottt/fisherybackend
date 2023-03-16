package com.example.fisherybackend.payloads.request;

import com.example.fisherybackend.entities.FisheringMade;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;

import java.sql.Blob;
import java.util.List;

@Getter
public class MembersRequest {

    @NotBlank
    @NotNull
    private Long userId;

    private List<FisheringMade> fisheringMade;
    private String username;
    private String firstName;

    private String lastName;
    private String password;
    private AccessLevel accessLevel;
    private Long hoursLogged;
    private Blob profilePicture;

    //blockchain
    private String membersHash;

}
