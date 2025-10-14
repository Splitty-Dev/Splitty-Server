package com.chaegangjo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CursorPageResponse<T> {

    T data;
    boolean hasNext = false;

    public CursorPageResponse(T data, boolean hasNext) {
        this.data = data;
        this.hasNext = hasNext;
    }
}