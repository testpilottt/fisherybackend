package com.example.fisherybackend.entities;

import com.example.fisherybackend.enums.AccessLevel;
import com.example.fisherybackend.enums.Country;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Members {
    @Id
    @GeneratedValue
    private Long memberId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<FisheringMade> fisheringMade;

    private String username;
    private String firstName;

    private String lastName;
    private String password;

    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    private Long hoursLogged;
    @Lob
    private Blob profilePicture;

    //blockchain
    private String membersHash;

    @Transient
    private Country country;

    public Members(long memberId) {
        this.memberId = memberId;
    }
}
