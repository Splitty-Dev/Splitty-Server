package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long>, GroupMemberCustomRepository {

    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);

    List<GroupMember> findAllByMemberId(Long memberId);

    Optional<GroupMember> findByGroupIdAndMemberId(Long groupId, Long memberId);

    int countByGroupId(Long groupId);
}
