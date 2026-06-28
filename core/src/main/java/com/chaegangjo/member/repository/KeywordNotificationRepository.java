package com.chaegangjo.member.repository;

import com.chaegangjo.member.domain.KeywordNotification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeywordNotificationRepository extends JpaRepository<KeywordNotification, Long> {

    List<KeywordNotification> findAllByMember_IdOrderByCreatedAtDesc(Long memberId);

    boolean existsByMember_IdAndKeyword(Long memberId, String keyword);

    long countByMember_Id(Long memberId);

    @Query("SELECT k FROM KeywordNotification k JOIN FETCH k.member "
            + "WHERE :goodsName LIKE CONCAT('%', k.keyword, '%')")
    List<KeywordNotification> findAllMatchingGoodsName(@Param("goodsName") String goodsName);
}
