package com.illumina.shanqyeet.flashcarddemo.repositories;

import com.illumina.shanqyeet.flashcarddemo.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
}
