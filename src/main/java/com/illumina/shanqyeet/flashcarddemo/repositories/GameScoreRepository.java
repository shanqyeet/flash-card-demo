package com.illumina.shanqyeet.flashcarddemo.repositories;

import com.illumina.shanqyeet.flashcarddemo.models.GameScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScoreEntity, Long> {
   List<GameScoreEntity> findTop100ByOrderByScoreDesc();
}
