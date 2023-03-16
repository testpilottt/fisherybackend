package com.example.fisherybackend.repository;

import com.example.fisherybackend.entities.FisheringMade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfFishRepository extends JpaRepository<TypeOfFishRepository, Long> {
}
