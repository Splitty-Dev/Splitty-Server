package com.chaegangjo.member.repository;

import com.chaegangjo.member.domain.SearchHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findTop10ByMember_IdOrderByCreatedAtDesc(Long memberId);
}
