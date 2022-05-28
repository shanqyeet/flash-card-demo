package com.illumina.shanqyeet.flashcarddemo.models;

import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_scores")
public class GameScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private UUID userId;
    private Integer score;
    private Integer penalty;
    private LocalTime averageAnswerSpeed;
    @Enumerated(value = EnumType.STRING)
    private GameDifficulty difficulty;
    private LocalDateTime createdAt;
}
