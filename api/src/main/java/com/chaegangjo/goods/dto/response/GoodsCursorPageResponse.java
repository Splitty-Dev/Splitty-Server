package com.chaegangjo.goods.dto.response;

import com.chaegangjo.dto.CursorPageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GoodsCursorPageResponse<T> extends CursorPageResponse<T> {

    NextCursor nextCursor;

    @Builder
    public GoodsCursorPageResponse(T data, boolean hasNext, NextCursor nextCursor) {
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
