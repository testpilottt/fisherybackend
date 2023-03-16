package com.example.fisherybackend.service;

import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import org.springframework.stereotype.Service;

@Service
public interface FisheringMadeService {

    CommonResponse createFisheringMade(FisheringMadeRequest fisheringMadeRequest);

}
