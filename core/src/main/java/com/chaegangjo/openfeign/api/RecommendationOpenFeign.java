package com.chaegangjo.openfeign.api;

import com.chaegangjo.openfeign.dto.RecommendGoodsRequest;
import com.chaegangjo.openfeign.dto.RecommendGoodsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "RecommendationSystem", url="${recommendation.url}")
public interface RecommendationOpenFeign {

    @PostMapping("/recommend")
    RecommendGoodsResponse recommendGoods(
            @RequestBody RecommendGoodsRequest request
    );
}
