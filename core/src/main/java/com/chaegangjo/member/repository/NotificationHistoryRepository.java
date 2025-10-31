package com.chaegangjo.member.repository;

import com.chaegangjo.member.domain.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long>, NotificationHistoryCustomRepository {
}
