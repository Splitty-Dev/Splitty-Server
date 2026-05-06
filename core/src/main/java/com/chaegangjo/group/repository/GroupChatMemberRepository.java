package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupChatMemberRepository extends JpaRepository<GroupChatMember, Long> {

    List<GroupChatMember> findAllByChatRoomId(Long chatRoomId);

    java.util.Optional<GroupChatMember> findByChatRoomIdAndMemberId(Long chatRoomId, Long memberId);
}
