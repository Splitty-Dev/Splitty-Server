package com.chaegangjo.member.service;

import com.chaegangjo.exception.MemberException;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.repository.MemberRepository;
import com.chaegangjo.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.MemberErrorCode.MEMBER_NOT_FOUND;

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

    public Member findMemberById(Long memberId) {
        return findById(memberId);
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}
