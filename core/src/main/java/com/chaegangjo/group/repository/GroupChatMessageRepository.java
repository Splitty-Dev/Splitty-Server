package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long>, GroupChatMessageCustomRepository {
}
