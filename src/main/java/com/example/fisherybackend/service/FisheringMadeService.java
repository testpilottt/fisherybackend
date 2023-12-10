package com.example.fisherybackend.service;

import com.example.fisherybackend.entities.HarvestedFishRecords;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FisheringMadeService {

    CommonResponse createFisheringMade(FisheringMadeRequest fisheringMadeRequest);

    List<HarvestedFishRecords> getFisheringMadeByMemberId(Long memberId);

}
