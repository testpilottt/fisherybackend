package com.example.fisherybackend.entities;

import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.enums.Region;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FisheringMade {

    @Id
    @GeneratedValue
    private Long fisheringId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Members members;

    private String location;

    @Lob
    private Blob pictureOfFish;

    private Integer weightKg;

    private Country country;

    private Region region;

    @OneToOne
    private TypeOfFish typeOfFish;

    //blockchain
    private String fisheringHash;
}
