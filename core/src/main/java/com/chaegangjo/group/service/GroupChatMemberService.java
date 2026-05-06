package com.chaegangjo.group.service;

import com.chaegangjo.exception.GroupException;
import com.chaegangjo.group.domain.GroupChatMember;
import com.chaegangjo.group.domain.GroupChatRoom;
import com.chaegangjo.group.repository.GroupChatMemberRepository;
import com.chaegangjo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.chaegangjo.exception.errorcode.GroupErrorCode.NOT_GROUP_MEMBER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupChatMemberService {

    private final GroupChatMemberRepository groupChatMemberRepository;

    @Transactional
    public GroupChatMember saveGroupChatMember(GroupChatRoom chatRoom, Member member) {
        return groupChatMemberRepository.save(new GroupChatMember(chatRoom, member));
    }

    public List<GroupChatMember> findAllByChatRoomId(Long chatRoomId) {
        return groupChatMemberRepository.findAllByChatRoomId(chatRoomId);
    }

    public GroupChatMember findByChatRoomIdAndMemberId(Long chatRoomId, Long memberId) {
        return groupChatMemberRepository.findByChatRoomIdAndMemberId(chatRoomId, memberId)
                .orElseThrow(() -> new GroupException(NOT_GROUP_MEMBER));
    }
}
