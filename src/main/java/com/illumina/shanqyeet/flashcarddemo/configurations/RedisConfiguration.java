package com.illumina.shanqyeet.flashcarddemo.configurations;

import com.illumina.shanqyeet.flashcarddemo.properties.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
    private RedisServer redisServer;

    public RedisConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
            RedisProperties redisProperties) {
        return new LettuceConnectionFactory(
                redisProperties.getRedisHost(),
                redisProperties.getRedisPort());
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<String>(String.class));
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }



    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}

