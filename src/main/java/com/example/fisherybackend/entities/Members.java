package com.example.fisherybackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<FisheringMade> fisheringMade;

    private String username;
    private String firstName;

    private String lastName;
    private String password;
    private AccessLevel accessLevel;
    private Long hoursLogged;
    @Lob
    private Blob profilePicture;

    //blockchain
    private String membersHash;

}
