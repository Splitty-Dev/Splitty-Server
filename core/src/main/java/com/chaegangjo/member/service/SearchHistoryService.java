package com.chaegangjo.member.service;

import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.SearchHistory;
import com.chaegangjo.member.repository.SearchHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> findTop10ByMemberId(Long memberId) {
        return searchHistoryRepository.findTop10ByMember_IdOrderByCreatedAtDesc(memberId);
    }

    @Transactional
    public void saveSearchHistory(Member member, String keyword) {
        SearchHistory searchHistory = new SearchHistory(keyword, member);
        searchHistoryRepository.save(searchHistory);
    }
}
