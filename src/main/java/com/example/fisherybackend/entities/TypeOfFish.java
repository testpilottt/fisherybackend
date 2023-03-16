package com.example.fisherybackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypeOfFish {
    @Id
    @GeneratedValue
    private Long typeOfFishId;

    private String typeOfFishName;
    @Lob
    private Blob typeOfFishPicture;

    private Boolean active;

    @OneToOne
    private FisheringMade fisheringMade;

}
