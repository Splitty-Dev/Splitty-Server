package com.chaegangjo.goods.dto.response;

import com.chaegangjo.dto.CursorPageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GoodsCursorPageInfo<T> extends CursorPageInfo<T> {

    NextCursor nextCursor;

    @Builder
    public GoodsCursorPageInfo(T data, boolean hasNext, NextCursor nextCursor) {
        super(data, hasNext);
        this.nextCursor = nextCursor;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class NextCursor {

        @Schema(example = "10")
        Long lastId;
    }
}
