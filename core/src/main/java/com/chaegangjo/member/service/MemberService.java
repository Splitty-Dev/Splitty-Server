package com.chaegangjo.member.service;

import com.chaegangjo.exception.MemberException;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.repository.MemberRepository;
import com.chaegangjo.openfeign.api.TMapOpenFeign;
import com.chaegangjo.openfeign.dto.TMapReverseGeocoding;
import com.chaegangjo.redis.RedisProperties;
import com.chaegangjo.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

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
        redisUtil.saveLocation(RedisProperties.MEMBER_KEY, id, latitude, longitude);

        //T Map Reverse Geocoding API 호출
        TMapReverseGeocoding reverseGeocoding = tMapOpenFeign.reverseGeocoding(
                version,
                String.valueOf(latitude), String.valueOf(longitude),
                addressType,
                appKey);

        Member member = findMemberById(id);
        member.setNeighName(reverseGeocoding.addressInfo().adminDong());

        return member;
    }

    public Member findMemberById(Long memberId) {
        return findById(memberId);
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}
