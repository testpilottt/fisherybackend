package com.example.fisherybackend.entities;

import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.enums.Region;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FisheringMade {

    @Id
    @GeneratedValue
    private Long fisheringId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Members members;

    private String location;

    @Lob
    @JsonIgnore
    private Blob pictureOfFish;

    private Double weightKg;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Region region;

    @OneToOne
    private TypeOfFish typeOfFish;

    //blockchain
    private String fisheringHash;

    private LocalDateTime timeLog;

    @Transient
    private String pictureOfFishBase64;
}
