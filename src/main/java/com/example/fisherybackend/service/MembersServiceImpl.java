package com.example.fisherybackend.service;

import com.example.fisherybackend.entities.Members;
import com.example.fisherybackend.repository.MembersRepository;
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
}
