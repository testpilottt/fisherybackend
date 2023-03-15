package com.example.fisherybackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Members {
    @Id
    @GeneratedValue
    private Long userId;

    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private Long hoursLogged;
    @Lob
    private Blob profilePicture;
}
