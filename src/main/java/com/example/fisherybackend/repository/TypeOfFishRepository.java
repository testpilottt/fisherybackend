package com.example.fisherybackend.repository;

import com.example.fisherybackend.entities.TypeOfFish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfFishRepository extends JpaRepository<TypeOfFish, Long> {
    TypeOfFish findByTypeOfFishName(String typeOfFishName);
}
