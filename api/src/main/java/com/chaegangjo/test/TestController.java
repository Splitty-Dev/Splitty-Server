package com.chaegangjo.test;

import static com.chaegangjo.redis.RedisProperties.GOODS_KEY;
import static com.chaegangjo.redis.RedisProperties.MEMBER_KEY;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.service.GoodsService;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.service.MemberService;
import com.chaegangjo.redis.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트", description = "테스트용")
@RequiredArgsConstructor
@RestController
public class TestController {

    private final RedisUtil redisUtil;
    private final MemberService memberService;
    private final GoodsService goodsService;

    @Operation(summary = "[백엔드 테스트용] 상품 좌표 및 카테고리 저장")
    @PostMapping("/test-save-goods-location")
    @Transactional
    public Point testSaveGoodsLocation(
            @RequestParam Long goodsId) {
        Goods goods = goodsService.findGoodsById(goodsId);
        String s = goodsId + ":" + goods.getCategory().getId();
        Point point = redisUtil.getPoint(GOODS_KEY, s);
        double longitude = goods.getLongitude();
        double latitude = goods.getLatitude();
        Point newPoint = new Point(longitude, latitude);
        if (point != null) {
            goods.setLocation(point);
            return point;
        }
        redisUtil.saveGoodsLocation(goodsId, goods.getCategory().getId(), newPoint);
        return newPoint;
    }

    @Operation(summary = "[백엔드 테스트용] 회원 좌표 저장")
    @PostMapping("/test-save-member-location")
    @Transactional
    public Point testSaveMemberLocation(
            @RequestParam Long memberId) {
        Point point = redisUtil.getPoint(MEMBER_KEY, String.valueOf(memberId));
        Member member = memberService.findMemberById(memberId);
        double longitude = member.getLongitude();
        double latitude = member.getLatitude();
        Point newPoint = new Point(longitude, latitude);
        if (point != null) {
            member.setLocation(point);
            return point;
        }
        redisUtil.saveMemberLocation(memberId, newPoint);
        return newPoint;
    }
}
