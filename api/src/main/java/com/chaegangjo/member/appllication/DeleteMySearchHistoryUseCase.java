package com.chaegangjo.member.appllication;

import static com.chaegangjo.exception.errorcode.SearchHistoryErrorCode.OWNER_MISMATCH;

import com.chaegangjo.exception.SearchHistoryException;
import com.chaegangjo.member.domain.SearchHistory;
import com.chaegangjo.member.dto.SearchHistoryInfo;
import com.chaegangjo.member.service.SearchHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteMySearchHistoryUseCase {

    private final SearchHistoryService searchHistoryService;

    public List<SearchHistoryInfo> single(Long memberId, Long searchHistoryId) {
        SearchHistory searchHistory = searchHistoryService.findById(searchHistoryId);

        if (!searchHistory.getMember().getId().equals(memberId)) {
            throw new SearchHistoryException(OWNER_MISMATCH);
        }

        searchHistoryService.deleteSearchHistory(memberId, searchHistoryId);

        return searchHistoryService.findTop10ByMemberId(memberId).stream()
                .map(SearchHistoryInfo::from)
                .toList();
    }

    public List<SearchHistoryInfo> all(Long memberId) {
        searchHistoryService.findTop10ByMemberId(memberId)
                .forEach(searchHistory ->
                        searchHistoryService.deleteSearchHistory(memberId, searchHistory.getId()));

        return searchHistoryService.findTop10ByMemberId(memberId).stream()
                .map(SearchHistoryInfo::from)
                .toList();
    }
}
