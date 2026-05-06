package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupChatRoomRepository extends JpaRepository<GroupChatRoom, Long> {

    Optional<GroupChatRoom> findByGroupId(Long groupId);
}
