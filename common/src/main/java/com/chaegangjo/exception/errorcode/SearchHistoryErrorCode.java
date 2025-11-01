package com.chaegangjo.exception.errorcode;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SearchHistoryErrorCode implements BaseErrorCode {

    SEARCH_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "SEARCH-HISTORY-001", "존재하지 않는 검색 기록입니다."),
    OWNER_MISMATCH(HttpStatus.BAD_REQUEST, "SEARCH-HISTORY-002", "본인의 검색 기록만 삭제할 수 있습니다.")
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}