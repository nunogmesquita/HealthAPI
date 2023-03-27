package HealthAPI.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("userCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("usersCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("clientCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("clientsCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));
    }

}