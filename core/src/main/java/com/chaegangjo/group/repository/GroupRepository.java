package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, GroupCustomRepository {

    Optional<Group> findByJoinCode(String joinCode);
}
