package com.example.fisherybackend.repository;

import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.entities.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FisheringMadeRepository extends JpaRepository<FisheringMade, Long> {

    List<FisheringMade> searchFisheringMadeByMembersMemberId(Long memberId);
}
