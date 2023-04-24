package com.example.fisherybackend.service;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MembersService {
    Optional<Members> memberLoginByUsernamePassword(String username, String password);

    CommonResponse createMember(MembersRequest membersRequest);

    CommonResponse updateMember(MembersRequest membersRequest);

    void deleteMember(Long memberId);


}
