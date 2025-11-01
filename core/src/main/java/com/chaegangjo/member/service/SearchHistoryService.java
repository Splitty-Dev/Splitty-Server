package com.chaegangjo.member.service;

import static com.chaegangjo.exception.errorcode.SearchHistoryErrorCode.SEARCH_HISTORY_NOT_FOUND;

import com.chaegangjo.exception.SearchHistoryException;
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

    public SearchHistory findById(Long searchHistoryId) {
        return searchHistoryRepository.findById(searchHistoryId)
                .orElseThrow(() -> new SearchHistoryException(SEARCH_HISTORY_NOT_FOUND));
    }

    public List<SearchHistory> findTop10ByMemberId(Long memberId) {
        return searchHistoryRepository.findTop10ByMember_IdOrderByCreatedAtDesc(memberId);
    }

    @Transactional
    public void saveSearchHistory(Member member, String keyword) {
        SearchHistory searchHistory = new SearchHistory(keyword, member);
        searchHistoryRepository.save(searchHistory);
    }

    @Transactional
    public void deleteSearchHistory(Long memberId, Long searchHistoryId) {
        searchHistoryRepository.deleteById(searchHistoryId);
    }
}
