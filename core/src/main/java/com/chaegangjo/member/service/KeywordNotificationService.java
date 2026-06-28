package com.chaegangjo.member.service;

import static com.chaegangjo.exception.errorcode.KeywordNotificationErrorCode.BLANK_KEYWORD;
import static com.chaegangjo.exception.errorcode.KeywordNotificationErrorCode.DUPLICATE_KEYWORD;
import static com.chaegangjo.exception.errorcode.KeywordNotificationErrorCode.KEYWORD_LIMIT_EXCEEDED;
import static com.chaegangjo.exception.errorcode.KeywordNotificationErrorCode.KEYWORD_NOTIFICATION_NOT_FOUND;
import static com.chaegangjo.exception.errorcode.KeywordNotificationErrorCode.OWNER_MISMATCH;

import com.chaegangjo.exception.KeywordNotificationException;
import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.member.domain.KeywordNotification;
import com.chaegangjo.member.domain.Member;
import com.chaegangjo.member.domain.Notification;
import com.chaegangjo.member.domain.NotificationHistory;
import com.chaegangjo.member.repository.KeywordNotificationRepository;
import com.chaegangjo.member.repository.NotificationHistoryRepository;
import com.chaegangjo.member.repository.NotificationRepository;
import com.chaegangjo.redis.RedisUtil;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KeywordNotificationService {

    private final KeywordNotificationRepository keywordNotificationRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final RedisUtil redisUtil;

    private static final int MAX_KEYWORDS_PER_MEMBER = 20;
    private static final int RESTRICT_DISTANCE = 300000000;
    private static final String NOTIFICATION_TITLE = "찾으시던 상품이 올라왔어요!";

    public List<KeywordNotification> findAllByMemberId(Long memberId) {
        return keywordNotificationRepository.findAllByMember_IdOrderByCreatedAtDesc(memberId);
    }

    @Transactional
    public KeywordNotification register(Member member, String keyword) {
        String normalized = normalize(keyword);

        if (keywordNotificationRepository.existsByMember_IdAndKeyword(member.getId(), normalized)) {
            throw new KeywordNotificationException(DUPLICATE_KEYWORD);
        }
        if (keywordNotificationRepository.countByMember_Id(member.getId()) >= MAX_KEYWORDS_PER_MEMBER) {
            throw new KeywordNotificationException(KEYWORD_LIMIT_EXCEEDED);
        }

        return keywordNotificationRepository.save(new KeywordNotification(normalized, member));
    }

    @Transactional
    public void delete(Long memberId, Long keywordNotificationId) {
        KeywordNotification keywordNotification = keywordNotificationRepository.findById(keywordNotificationId)
                .orElseThrow(() -> new KeywordNotificationException(KEYWORD_NOTIFICATION_NOT_FOUND));

        if (!keywordNotification.getMember().getId().equals(memberId)) {
            throw new KeywordNotificationException(OWNER_MISMATCH);
        }

        keywordNotificationRepository.delete(keywordNotification);
    }

    /**
     * 신규 상품 등록 시, 상품명과 일치하는 키워드를 구독하고 상품 반경 내에 있는 회원에게 알림을 생성한다.
     * 알림 생성 실패가 상품 등록 흐름을 막지 않도록 방어적으로 처리한다.
     */
    @Transactional
    public void notifyForNewGoods(Goods goods) {
        try {
            List<KeywordNotification> matched = keywordNotificationRepository.findAllMatchingGoodsName(goods.getName());
            if (matched.isEmpty()) {
                return;
            }

            Set<Long> nearByMemberIds = findNearByMemberIds(goods);
            if (nearByMemberIds.isEmpty()) {
                return;
            }

            Long sellerId = goods.getSeller().getId();

            // 한 회원이 여러 키워드를 등록했을 수 있으므로 회원당 첫 매칭 키워드 하나만 사용한다.
            Map<Long, KeywordNotification> targets = new LinkedHashMap<>();
            for (KeywordNotification keywordNotification : matched) {
                Long memberId = keywordNotification.getMember().getId();
                if (memberId.equals(sellerId) || !nearByMemberIds.contains(memberId)) {
                    continue;
                }
                targets.putIfAbsent(memberId, keywordNotification);
            }

            for (KeywordNotification target : targets.values()) {
                Notification notification = notificationRepository.save(
                        new Notification(NOTIFICATION_TITLE, buildBody(target.getKeyword(), goods.getName()), goods));
                notificationHistoryRepository.save(new NotificationHistory(target.getMember(), notification));
            }
        } catch (Exception e) {
            log.warn("키워드 알림 생성 실패 - goodsId: {}", goods.getId(), e);
        }
    }

    private Set<Long> findNearByMemberIds(Goods goods) {
        if (goods.getLongitude() == null || goods.getLatitude() == null) {
            return Set.of();
        }
        Point goodsPoint = new Point(goods.getLongitude(), goods.getLatitude());
        return new HashSet<>(redisUtil.getNearByMemberIds(goodsPoint, RESTRICT_DISTANCE));
    }

    private String buildBody(String keyword, String goodsName) {
        return "'" + keyword + "' 키워드의 상품 '" + goodsName + "'이(가) 근처에 등록되었어요.";
    }

    private String normalize(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new KeywordNotificationException(BLANK_KEYWORD);
        }
        return keyword.trim();
    }
}
