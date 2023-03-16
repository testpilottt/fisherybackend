package com.example.fisherybackend.repository;

import com.example.fisherybackend.entities.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByUsernameAndPassword(String username, String password);

}
