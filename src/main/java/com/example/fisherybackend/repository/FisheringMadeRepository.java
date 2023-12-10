package com.example.fisherybackend.repository;

import com.example.fisherybackend.entities.HarvestedFishRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FisheringMadeRepository extends JpaRepository<HarvestedFishRecords, Long> {

    List<HarvestedFishRecords> searchFisheringMadeByMembersMemberId(Long memberId);
}
