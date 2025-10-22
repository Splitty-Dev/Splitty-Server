package com.chaegangjo.redis;

import com.chaegangjo.exception.MemberException;
import com.chaegangjo.exception.errorcode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveValue(String key, Object value, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public void saveLocation(String key, Long id, double latitude, double longitude) {
        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        Point point = new Point(longitude, latitude);
        geoOperations.add(key, point, String.valueOf(id));
    }

    public List<Long> getNearByIds(Long memberId, int restrictDistance) {

        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();

        Point memberPoint = geoOperations.position(RedisProperties.MEMBER_KEY, memberId.toString()).get(0);
        if (memberPoint == null) {
            throw new MemberException(MemberErrorCode.MEMBER_LOCATION_NOT_FOUND);
        }
        GeoReference<Object> reference = GeoReference.fromCoordinate(memberPoint);

        Distance radius = new Distance(restrictDistance, Metrics.METERS); // 반경 범위 설정

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .sortAscending();

        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = geoOperations
                .search(RedisProperties.GOODS_KEY, reference, radius, args);

        List<Long> nearByIds = new ArrayList<>();

        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> result : results) {
            RedisGeoCommands.GeoLocation<Object> location = result.getContent();

            Long id = Long.valueOf(location.getName().toString());
//            double distance = result.getDistance().getValue();

            nearByIds.add(id);
        }

        return nearByIds;
    }
}
