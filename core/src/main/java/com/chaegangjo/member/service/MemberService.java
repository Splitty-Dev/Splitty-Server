package com.chaegangjo.member.service;

import static com.chaegangjo.exception.errorcode.MemberErrorCode.MEMBER_LOCATION_NOT_FOUND;
import static com.chaegangjo.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.chaegangjo.redis.RedisProperties.MEMBER_KEY;

import com.chaegangjo.exception.MemberException;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.repository.MemberRepository;
import com.chaegangjo.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member saveMemberLocation(Long id, double latitude, double longitude, String adminDong) {
        Point point = new Point(longitude, latitude);
        redisUtil.saveMemberLocation(id, point);

        Member member = findMemberById(id);
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
}
