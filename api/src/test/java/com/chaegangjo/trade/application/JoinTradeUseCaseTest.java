package com.chaegangjo.trade.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.chaegangjo.goods.application.SaveGoodsUseCase;
import com.chaegangjo.goods.domain.Category;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.repository.CategoryRepository;
import com.chaegangjo.goods.repository.GoodsRepository;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.repository.MemberRepository;
import com.chaegangjo.trade.dto.JoinTradeRequest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class JoinTradeUseCaseTest {

    @Autowired
    JoinTradeUseCase joinTradeUseCase;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    SaveGoodsUseCase saveGoodsUseCase;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Long goodsId;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.findById(1L).get();
        Category category = categoryRepository.findById(1L).get();

        Goods goods = new Goods(
                member,
                category,
                "테스트 설명",
                10000,
                100,
                "테스트 상품",
                2,
                "테스트 위치",
                "image1.jpg"
        );

        Goods newGoods = goodsRepository.save(goods);
        goodsId = newGoods.getId();
    }

    @Test
    void 동시에_10명이_참여하면_단_2명만_성공해야_한다() throws Exception {
        int threadCount = 5;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger fail = new AtomicInteger(0);

        for (int i = 2; i <= threadCount + 1; i++) {
            long memberId = i;

            executor.submit(() -> {
                System.out.println("Thread " + Thread.currentThread().getName() + " started");
                try {
                    joinTradeUseCase.execute(new JoinTradeRequest(goodsId, 1), memberId);
                    System.out.println("Thread " + Thread.currentThread().getName() + " success");
                    success.incrementAndGet();
                } catch (Exception e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " fail: " + e);
                    fail.incrementAndGet();
                } finally {
                    latch.countDown();
                    System.out.println("Thread " + Thread.currentThread().getName() + " finished");
                }
            });
        }

        latch.await();
        executor.shutdown();

        System.out.println("성공 = " + success.get());
        System.out.println("실패 = " + fail.get());

        assertThat(success.get()).isEqualTo(2);

        Goods getGoods = goodsRepository.findById(goodsId).get();
        assertThat(getGoods.getCurrParticipants()).isEqualTo(3);
        assertThat(getGoods.getLeftQuantity()).isEqualTo(0);
    }
}