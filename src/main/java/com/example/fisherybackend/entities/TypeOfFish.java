package com.example.fisherybackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.sql.Clob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfFish {
    @Id
    @GeneratedValue
    private Long typeOfFishId;

    @Column(unique=true)
    private String typeOfFishName;

    @Lob
    @JsonIgnore
    private Blob typeOfFishPicture;

    private Boolean active;

    @Transient
    private String typeOfFishPictureBase64;
}
