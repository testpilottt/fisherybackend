package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.payloads.request.TypeOfFishRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.TypeOfFishRepository;
import com.example.fisherybackend.service.TypeOfFishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class TypeOfFishServiceImpl implements TypeOfFishService {

    @Autowired
    private TypeOfFishRepository typeOfFishRepository;

    @Override
    public CommonResponse createTypeOfFish(TypeOfFishRequest typeOfFishRequest) {
        TypeOfFish typeOfFish = new TypeOfFish();

        typeOfFish.setTypeOfFishName(typeOfFishRequest.getTypeOfFishName());
        try {
            Blob blob = new SerialBlob(typeOfFishRequest.getTypeOfFishPicture());
            typeOfFish.setTypeOfFishPicture(blob);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        typeOfFish.setActive(typeOfFishRequest.getIsActive());

        if (Objects.nonNull(typeOfFishRepository.findByTypeOfFishName(typeOfFish.getTypeOfFishName()))) {
            return new CommonResponse("Name of fish must be unique", false);
        }

        typeOfFishRepository.save(typeOfFish);

        return new CommonResponse("New TypeOfFish Created");
    }

    @Override
    public CommonResponse setActiveStatusOfTypeOfFish(Long typeOfFishId, Boolean isActive) {
        typeOfFishRepository.findById(typeOfFishId)
                .ifPresent(p -> p.setActive(isActive));

        return new CommonResponse("TypeOfFish set to = " + isActive.toString());
    }

    @Override
    public List<TypeOfFish> getAllTypeOfFish() {
        return typeOfFishRepository.findAll();
    }
}
