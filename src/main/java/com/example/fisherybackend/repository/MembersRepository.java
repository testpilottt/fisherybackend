package com.example.fisherybackend.repository;

import com.example.fisherybackend.entities.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByUsernameAndPassword(String username, String password);
    Optional<Members> findByUsername(String username);

}
