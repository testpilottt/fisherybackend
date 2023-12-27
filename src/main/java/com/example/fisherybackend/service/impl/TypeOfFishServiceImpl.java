package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.entities.TypeOfFish;
import com.example.fisherybackend.enums.CommonResponseReason;
import com.example.fisherybackend.payloads.request.TypeOfFishRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.TypeOfFishRepository;
import com.example.fisherybackend.service.TypeOfFishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Transactional
@Component
public class TypeOfFishServiceImpl implements TypeOfFishService {

    @Autowired
    private TypeOfFishRepository typeOfFishRepository;

    @Override
    public CommonResponse createOrUpdateTypeOfFish(TypeOfFishRequest typeOfFishRequest) {
        TypeOfFish typeOfFish = new TypeOfFish();

        typeOfFish.setTypeOfFishName(typeOfFishRequest.getTypeOfFishName());
        if (Objects.nonNull(typeOfFishRequest.getTypeOfFishPicture())) {
            try {
                Blob blob = new SerialBlob(typeOfFishRequest.getTypeOfFishPicture());
                typeOfFish.setTypeOfFishPicture(blob);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        typeOfFish.setActive(typeOfFishRequest.getIsActive());

        TypeOfFish existingTypeOfFish = typeOfFishRepository.findByTypeOfFishName(typeOfFish.getTypeOfFishName());

        if (Objects.nonNull(existingTypeOfFish)) {
            //Is Update?
            if (typeOfFish.getActive().equals(existingTypeOfFish.getActive()) &&
                    typeOfFish.getTypeOfFishPicture().equals(existingTypeOfFish.getTypeOfFishPicture())) {
                return new CommonResponse("Name of fish must be unique", false, CommonResponseReason.NAME_OF_FISH_NOTUNIQUE);
            } else {
                existingTypeOfFish.setTypeOfFishPicture(typeOfFish.getTypeOfFishPicture());
                existingTypeOfFish.setActive(typeOfFish.getActive());
                typeOfFishRepository.save(existingTypeOfFish);
                return new CommonResponse("TypeOfFish saved");
            }
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
