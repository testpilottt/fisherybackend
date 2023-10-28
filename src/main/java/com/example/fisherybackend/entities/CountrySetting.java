package com.example.fisherybackend.entities;

import com.example.fisherybackend.enums.Country;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountrySetting {
    @Id
    @GeneratedValue
    private Long countrySettingId;

    @Enumerated(EnumType.STRING)
    private Country country;

    private String latitude;
    private String longtitude;
}
