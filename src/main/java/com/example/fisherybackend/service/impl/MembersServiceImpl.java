package com.example.fisherybackend.service.impl;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.repository.MembersRepository;
import com.example.fisherybackend.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MembersServiceImpl implements MembersService {

    @Autowired
    private MembersRepository membersRepository;

    @Override
    public Optional<Members> memberLoginByUsernamePassword(String username, String password) {
        return membersRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public CommonResponse createMember(MembersRequest membersRequest) {

        Members member = new Members();

        member.setUsername(membersRequest.getUsername());
        member.setPassword(membersRequest.getPassword());
        member.setProfilePicture(membersRequest.getProfilePicture());
        member.setFirstName(membersRequest.getFirstName());
        member.setLastName(membersRequest.getLastName());
        member.setHoursLogged(membersRequest.getHoursLogged());
        member.setMembersHash(membersRequest.getMembersHash());

        membersRepository.save(member);

        return new CommonResponse("New Member Created");
    }
}
