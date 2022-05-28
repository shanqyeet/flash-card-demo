package com.illumina.shanqyeet.flashcarddemo.repositories;

import com.illumina.shanqyeet.flashcarddemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
