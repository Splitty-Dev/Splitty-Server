package com.chaegangjo.member.service;

import static com.chaegangjo.exception.errorcode.MemberErrorCode.MEMBER_LOCATION_NOT_FOUND;
import static com.chaegangjo.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.chaegangjo.firebase.FirebaseProperties.FCM_REDIS_KEY;
import static com.chaegangjo.redis.RedisProperties.MEMBER_KEY;

import com.chaegangjo.exception.MemberException;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.repository.MemberRepository;
import com.chaegangjo.openfeign.api.TMapOpenFeign;
import com.chaegangjo.openfeign.dto.TMapReverseGeocodingResponse;
import com.chaegangjo.redis.RedisUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;
    private final TMapOpenFeign tMapOpenFeign;

    @Value("${tmap.app-key}")
    private String appKey;
    @Value("${tmap.reverse-geocoding.version}")
    private int version;
    @Value("${tmap.reverse-geocoding.address-type}")
    private String addressType;

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member saveMemberLocation(Long id, double latitude, double longitude) {
        Point point = new Point(longitude, latitude);
        redisUtil.saveMemberLocation(id, point);

        //T Map Reverse Geocoding API 호출
        TMapReverseGeocodingResponse reverseGeocoding = tMapOpenFeign.reverseGeocoding(
                version,
                String.valueOf(latitude), String.valueOf(longitude),
                addressType,
                appKey);

        Member member = findMemberById(id);
        String adminDong = reverseGeocoding.addressInfo().adminDong();
        member.setLocation(adminDong, point);

        return member;
    }

    public Point getMemberPoint(Long memberId) {
        Point memberPoint = redisUtil.getPoint(MEMBER_KEY, String.valueOf(memberId));
        if (memberPoint == null) {
            Member member = findMemberById(memberId);
            Double longitude = member.getLongitude();
            Double latitude = member.getLatitude();
            if (longitude == null || latitude == null) {
                throw new MemberException(MEMBER_LOCATION_NOT_FOUND);
            }
            redisUtil.saveMemberLocation(member.getId(), new Point(longitude, latitude));
        }
        return memberPoint;
    }

    public Member findMemberById(Long memberId) {
        return findById(memberId);
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }

    public void saveMyFcmToken(Long memberId, String fcmToken) {
        Member member = findById(memberId);
        member.setFcmToken(fcmToken);
        String key = FCM_REDIS_KEY + memberId;
        redisUtil.saveValue(key, fcmToken);
    }

    public String getOrRetrieveFcmToken(Long memberId) { //redis에 저장된 fcm token이 없다면 db에서 조회
        String key = FCM_REDIS_KEY + memberId;
        String token;
        Optional<Object> value = redisUtil.getValue(key);
        if (value.isEmpty()) {
            Member member = findMemberById(memberId);
            token = member.getFcmToken();
            if (token == null || token.isBlank()) {
                log.warn("[*] FCM 존재하지 않습니다. memberId: {}", memberId);
                return "";
            }
            redisUtil.saveValue(key, token);
        } else {
            token = (String) value.get();
        }

        return token;
    }

    public String getFcmToken(Long memberId) {
        String key = FCM_REDIS_KEY + memberId;
        Optional<Object> value = redisUtil.getValue(key);
        return value.orElse("").toString();
    }
}
