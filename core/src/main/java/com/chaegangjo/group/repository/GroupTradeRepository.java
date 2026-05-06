package com.chaegangjo.group.repository;

import com.chaegangjo.group.domain.GroupTrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupTradeRepository extends JpaRepository<GroupTrade, Long>, GroupTradeCustomRepository {
}
