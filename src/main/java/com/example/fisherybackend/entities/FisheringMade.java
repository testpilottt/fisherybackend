package com.example.fisherybackend.entities;

import com.example.fisherybackend.enums.Country;
import com.example.fisherybackend.enums.Region;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    @JoinColumn(name = "memberId")
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
    @JoinColumn(referencedColumnName = "typeOfFishId")
    private TypeOfFish typeOfFish;

    private LocalDateTime timeLog;

    @Transient
    private String pictureOfFishBase64;

    //Blockchain
    public int blockchainIndex;
    public Long timestamp;
    public String previousHash;
    public String hash;

    public FisheringMade(Country country) {
        this.country = country;
    }

    public FisheringMade(int blockchainIndex, FisheringMade data, String previousHash) {
        this.fisheringId = data.getFisheringId();
        this.members = data.getMembers();
        this.location = data.getLocation();
        this.pictureOfFish = data.getPictureOfFish();
        this.weightKg = data.getWeightKg();
        this.country = data.getCountry();
        this.region = data.getRegion();
        this.typeOfFish = data.getTypeOfFish();
        this.timeLog = data.getTimeLog();
        this.pictureOfFishBase64 = data.getPictureOfFishBase64();


        this.blockchainIndex = blockchainIndex;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String text = blockchainIndex + previousHash + timestamp +
                fisheringId + members + location + pictureOfFish + weightKg + country + region
                + typeOfFish + timeLog;

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        final StringBuilder hexString = new StringBuilder();
        final byte[] bytes = digest.digest(text.getBytes());

        for (final byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
