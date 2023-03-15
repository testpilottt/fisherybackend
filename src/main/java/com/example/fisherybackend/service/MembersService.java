package com.example.fisherybackend.service;

import com.example.fisherybackend.entities.Members;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MembersService {
    Optional<Members> memberLoginByUsernamePassword(String username, String password);

}
