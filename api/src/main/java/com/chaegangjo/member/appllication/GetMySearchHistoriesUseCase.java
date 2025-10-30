package com.chaegangjo.member.appllication;

import com.chaegangjo.member.dto.SearchHistoryInfo;
import com.chaegangjo.member.repository.SearchHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetMySearchHistoriesUseCase {

    private final SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistoryInfo> execute(Long memberId) {
        return searchHistoryRepository.findTop10ByMember_IdOrderByCreatedAtDesc(memberId).stream()
                .map(SearchHistoryInfo::from)
                .toList();
    }
}
