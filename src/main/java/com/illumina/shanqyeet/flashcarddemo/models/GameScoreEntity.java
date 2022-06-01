package com.illumina.shanqyeet.flashcarddemo.models;

import com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_scores")
public class GameScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private UUID userId;
    private String username;
    private Integer score;
    private Integer penalty;
    @Column(name = "average_answer_time")
    private Long averageAnswerTimeInMillis;
    @Enumerated(EnumType.STRING)
    private GameDifficulty.MathTableGame gameDifficulty;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){ this.createdAt = LocalDateTime.now();}
}
