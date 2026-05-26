package com.chaegangjo.cache;

import com.chaegangjo.product.dto.ProductInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class CacheConfig {

    private static final String CACHE_PREFIX = "splitty:v2:";
    private static final String PRODUCT_AUTOCOMPLETE_CACHE = "product-autocomplete";

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(24))
                .computePrefixWith(cacheName -> CACHE_PREFIX + cacheName + "::")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        RedisCacheConfiguration productAutocompleteConfig = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(productAutocompleteSerializer(objectMapper))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .withInitialCacheConfigurations(Map.of(PRODUCT_AUTOCOMPLETE_CACHE, productAutocompleteConfig))
                .build();
    }

    private Jackson2JsonRedisSerializer<List<ProductInfo>> productAutocompleteSerializer(ObjectMapper objectMapper) {
        JavaType javaType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, ProductInfo.class);
        return new Jackson2JsonRedisSerializer<>(objectMapper, javaType);
    }
}
