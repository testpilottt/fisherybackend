package com.example.fisherybackend.controller;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.payloads.request.MembersRequest;
import com.example.fisherybackend.service.MembersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Transactional
@RestController
@RequestMapping("/fishery")
public class MembersController {

    @Autowired
    private MembersService membersService;

    @PostMapping("/membersLogin")
    public ResponseEntity<HttpStatus> memberLoginByUsernamePassword(@RequestBody MembersRequest membersRequest){

        Optional<Members> member = membersService.memberLoginByUsernamePassword(membersRequest.getUsername(), membersRequest.getPassword());

        return member.isPresent() ? new ResponseEntity<>(HttpStatus.ACCEPTED) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
