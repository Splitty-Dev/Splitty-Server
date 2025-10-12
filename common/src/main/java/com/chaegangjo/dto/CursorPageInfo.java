package com.chaegangjo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CursorPageInfo<T> {

    T data;
    boolean hasNext = false;

    public CursorPageInfo(T data, boolean hasNext) {
        this.data = data;
        this.hasNext = hasNext;
    }
}