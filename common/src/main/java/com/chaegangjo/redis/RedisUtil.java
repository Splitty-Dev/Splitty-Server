package com.chaegangjo.redis;

import static com.chaegangjo.redis.RedisProperties.GOODS_KEY;
import static com.chaegangjo.redis.RedisProperties.MEMBER_KEY;

import com.chaegangjo.exception.MemberException;
import com.chaegangjo.exception.errorcode.MemberErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Component;

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

    public void saveLocation(String key, Long id, Point point) {
        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        geoOperations.add(key, point, String.valueOf(id));
    }

    public void saveMemberLocation(Long id, Point point) {
        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        geoOperations.add(MEMBER_KEY, point, String.valueOf(id));
    }

    public void saveGoodsLocation(Long goodsId, Long categoryId, Point point) {
        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        String value = goodsId + ":" + categoryId;
        geoOperations.add(GOODS_KEY, point, value);
    }

    public List<Long> getNearByIds(Long memberId, int restrictDistance) {
        GeoResults<GeoLocation<Object>> results = getGeoResults(
                memberId, restrictDistance);

        List<Long> nearByIds = new ArrayList<>();

        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> result : results) {
            RedisGeoCommands.GeoLocation<Object> location = result.getContent();

            String[] value = location.getName().toString().split(":");
            Long goodsId = Long.parseLong(value[0]);

            nearByIds.add(goodsId);
        }

        return nearByIds;
    }

    public List<Long> getNearByIds(Long memberId, int restrictDistance, Long selectedCategoryId) {
        GeoResults<GeoLocation<Object>> results = getGeoResults(
                memberId, restrictDistance);

        List<Long> nearByIds = new ArrayList<>();

        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> result : results) {
            RedisGeoCommands.GeoLocation<Object> location = result.getContent();

            String[] value = location.getName().toString().split(":");
            Long goodsId = Long.parseLong(value[0]);
            Long categoryId = Long.parseLong(value[1]);
            if (!categoryId.equals(selectedCategoryId)) {
                continue;
            }

            nearByIds.add(goodsId);
        }

        return nearByIds;
    }

    private GeoResults<GeoLocation<Object>> getGeoResults(Long memberId, int restrictDistance) {
        Point memberPoint = getPoint(MEMBER_KEY, memberId);
        if (memberPoint == null) {
            throw new MemberException(MemberErrorCode.MEMBER_LOCATION_NOT_FOUND);
        }
        GeoReference<Object> reference = GeoReference.fromCoordinate(memberPoint);

        Distance radius = new Distance(restrictDistance, Metrics.METERS); //반경 범위 설정

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .sortAscending();

        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        GeoResults<GeoLocation<Object>> results = geoOperations
                .search(GOODS_KEY, reference, radius, args);
        return results;
    }

    public Point getPoint(String key, Long id) {
        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        return geoOperations.position(key, id.toString()).get(0);
    }
}
