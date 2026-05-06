package com.chaegangjo.wishlist.application;

import com.chaegangjo.chat.dto.request.SaveWishItemRequest;
import com.chaegangjo.goods.application.SaveGoodsUseCase;
import com.chaegangjo.goods.dto.DetailGoodsInfo;
import com.chaegangjo.goods.dto.request.SaveGoodsRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetWishListUseCaseTest {

    @Autowired
    GetWishListUseCase getWishListUseCase;

    @Autowired
    SaveGoodsUseCase saveGoodsUseCase;

    @Autowired
    SaveWishListItemUseCase saveWishListItemUseCase;


    @Test
    void testQueryCount() {
        SaveGoodsRequest saveGoodsRequest = new SaveGoodsRequest(
                1L,
                "테스트 상품",
                "테스트 설명",
                10000,
                100,
                1,
                "테스트 위치",
                List.of("http://example.com/image1.jpg"));

        List<Long> goodsIds = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            DetailGoodsInfo goods = saveGoodsUseCase.execute(saveGoodsRequest, 1L);
            goodsIds.add(goods.id());
        }

        for (Long goodsId : goodsIds) {
            saveWishListItemUseCase.execute(1L, new SaveWishItemRequest(goodsId));
        }

        for (int i = 0; i < 100; i++) {
            getWishListUseCase.execute(1L, null, null);
        }

        System.out.println("start");
        long startTime = System.currentTimeMillis();
        getWishListUseCase.execute(1L, null, null);
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
