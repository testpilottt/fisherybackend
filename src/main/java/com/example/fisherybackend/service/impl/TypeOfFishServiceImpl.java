package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.request.TypeOfFishRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.MembersRepository;
import com.example.fisherybackend.repository.TypeOfFishRepository;
import com.example.fisherybackend.service.TypeOfFishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeOfFishServiceImpl implements TypeOfFishService {

    @Autowired
    private TypeOfFishRepository typeOfFishRepository;

    @Override
    public CommonResponse createTypeOfFish(TypeOfFishRequest typeOfFishRequest) {
        TypeOfFish typeOfFish = new TypeOfFish();

        typeOfFish.setTypeOfFishName(typeOfFishRequest.getTypeOfFishName());
        typeOfFish.setTypeOfFishPicture(typeOfFishRequest.getTypeOfFishPicture());
        typeOfFish.setActive(typeOfFishRequest.getIsActive());
        typeOfFish.setFisheringMade(typeOfFishRequest.getFisheringMade());

        typeOfFishRepository.save(typeOfFish);

        return new CommonResponse("New TypeOfFish Created");
    }

    @Override
    public CommonResponse setActiveStatusOfTypeOfFish(Long typeOfFishId, Boolean isActive) {
        typeOfFishRepository.findById(typeOfFishId)
                .ifPresent(p -> p.setActive(isActive));

        return new CommonResponse("TypeOfFish set to = " + isActive.toString());
    }
}
