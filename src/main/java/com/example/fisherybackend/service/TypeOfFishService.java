package com.example.fisherybackend.service;

import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.payloads.request.TypeOfFishRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeOfFishService {
    CommonResponse createOrUpdateTypeOfFish(TypeOfFishRequest typeOfFishRequest);

    CommonResponse setActiveStatusOfTypeOfFish(Long typeOfFishId, Boolean isActive);

    List<TypeOfFish> getAllTypeOfFish();

}
