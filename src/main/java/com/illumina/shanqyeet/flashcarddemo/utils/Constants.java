package com.illumina.shanqyeet.flashcarddemo.utils;

public abstract class Constants {
    public static final String STATUS_NEW = "new";
    public static final String STATUS_IN_PROGRESS = "in_progress";

    // GET NEXT CHALLENGE SERVICE
    public static final String CACHE_GAME_SESSION = "game_session";
    public static final String CACHE_GAME_DIFFICULTY = "game_difficulty";
    public static final String CACHE_NUM_PAIR_FREQUENCY = "number_pair_frequency";

    // NUMBER PAIR AND MIN MAX
    public static final int RANDOM_GENERATOR_MIN = 0;
    public static final int RANDOM_GENERATOR_MAX = 12;
    public static final int NUMPAIR_MAX_FREQUENCY_EASY_MEDIUM = 2;
    public static final int NUMPAIR_MAX_FREQUENCY_HARD = 5;
    public static final int MAX_CHALLENGE_COUNT_EASY_MEDIUM = 250;
    public static final int MAX_CHALLENGE_COUNT_EASY_HARD = 350;

    // SECURITY
    public static final String SECRET = "FlashCardJWTSecretKey";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final Long TOKEN_EXPIRY_TIME = Long.valueOf(3600_000);

    // Arithmetic Operators
    public static final char MINUS_CHAR = '-';
    public static final char PLUS_CHAR = '+';
    public static final char MULTIPLY_CHAR = '*';
    public static final char DIVIDE_CHAR = '/';
    public static final char MODULUS_CHAR = '%';

}
