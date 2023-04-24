package com.example.fisherybackend.service;

import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.request.TypeOfFishRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import org.springframework.stereotype.Service;

@Service
public interface TypeOfFishService {
    CommonResponse createTypeOfFish(TypeOfFishRequest typeOfFishRequest);

    CommonResponse setActiveStatusOfTypeOfFish(Long typeOfFishId, Boolean isActive);

}
