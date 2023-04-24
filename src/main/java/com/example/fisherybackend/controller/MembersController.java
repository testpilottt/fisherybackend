package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.payloads.response.CommonResponse;
import com.example.fisherybackend.service.MembersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Transactional
@RestController
@RequestMapping("/fishery")
public class MembersController {

    @Autowired
    private MembersService membersService;

    @PostMapping("/membersLogin")
    public ResponseEntity<Members> memberLoginByUsernamePassword(@RequestBody MembersRequest membersRequest) {

        Optional<Members> member = membersService.memberLoginByUsernamePassword(membersRequest.getUsername(), membersRequest.getPassword());

        Members reponseMember = new Members();

        if (!member.isPresent()) {
            reponseMember.setMemberId(-1L);
            return new ResponseEntity<>(reponseMember, HttpStatus.UNAUTHORIZED);
        } else {
            reponseMember.setMemberId(member.get().getMemberId());
            reponseMember.setAccessLevel(member.get().getAccessLevel());
            return new ResponseEntity<>(reponseMember, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/createMember")
    public ResponseEntity<CommonResponse> createMember(@RequestBody MembersRequest membersRequest){

        CommonResponse commonResponse = membersService.createMember(membersRequest);

        return new ResponseEntity<>(commonResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteMember/{memberId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("memberId") Long memberId) {
        membersService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateMember")
    public ResponseEntity<CommonResponse> updateUser(@RequestBody MembersRequest membersRequest)
    {
        CommonResponse commonResponse = membersService.updateMember(membersRequest);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}
