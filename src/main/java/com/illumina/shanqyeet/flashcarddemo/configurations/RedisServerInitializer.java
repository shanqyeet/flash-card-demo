//package com.illumina.shanqyeet.flashcarddemo.configurations;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import redis.embedded.RedisServer;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//@Configuration
//@Slf4j
//public class RedisServerInitializer {
//
//    private RedisServer redisServer;
//
//    public RedisServerInitializer(RedisProperties redisProperties) {
//        this.redisServer = new RedisServer(redisProperties.getRedisPort());
//    }
//
//    @PostConstruct
//    public void postConstruct() {
//        log.info("Starting Redis Server...");
//        redisServer.start();
//    }
//
//    @PreDestroy
//    public void preDestroy() {
//        log.info("Terminating Redis Server...");
//        redisServer.stop();
//    }
//}
