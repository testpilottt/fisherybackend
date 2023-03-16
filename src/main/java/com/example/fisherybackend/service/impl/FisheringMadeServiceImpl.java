package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.entities.FisheringMade;
import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.payloads.request.FisheringMadeRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.FisheringMadeRepository;
import com.example.fisherybackend.repository.MembersRepository;
import com.example.fisherybackend.service.FisheringMadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FisheringMadeServiceImpl implements FisheringMadeService {
    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private FisheringMadeRepository fisheringMadeRepository;

    @Override
    public CommonResponse createFisheringMade(FisheringMadeRequest fisheringMadeRequest) {

        FisheringMade fisheringMade = new FisheringMade();
        Members members = membersRepository.findById(fisheringMadeRequest.getMemberID()).get();

        fisheringMade.setMembers(members);
        fisheringMade.setCountry(fisheringMadeRequest.getCountry());
        fisheringMade.setLocation(fisheringMadeRequest.getLocation());
        fisheringMade.setRegion(fisheringMadeRequest.getRegion());
        fisheringMade.setWeightKg(fisheringMadeRequest.getWeightKg());
        fisheringMade.setPictureOfFish(fisheringMadeRequest.getPictureOfFish());
        fisheringMade.setTypeOfFish(fisheringMadeRequest.getTypeOfFish());
        fisheringMade.setFisheringHash(fisheringMadeRequest.getFisheringHash());

        fisheringMadeRepository.save(fisheringMade);

        return new CommonResponse("New Member Created");
    }
}
