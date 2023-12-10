package com.example.fisherybackend.repository;

import com.example.fisherybackend.entities.CountrySetting;
import com.example.fisherybackend.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountrySettingRepository extends JpaRepository<CountrySetting, Long> {
    CountrySetting searchCountrySettingByCountry(Country country);
}
