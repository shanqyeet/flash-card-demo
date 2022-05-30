package com.illumina.shanqyeet.flashcarddemo.repositories;

import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    UserEntity findById(UUID id);
}
