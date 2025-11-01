package com.chaegangjo.logger;

import com.chaegangjo.s3.RecommendationAmazonS3Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Scope("singleton")
public class S3ActionLoggerImpl implements UserActionLogger {

    private final RecommendationAmazonS3Service s3Service;

    private final List<Map<String, Object>> buffer = new ArrayList<>();
    private static final int MAX_EVENTS = 1000;

    @Override
    public synchronized void flush() {
        if (!buffer.isEmpty()) {
            flushToS3();
        }
    }

    private void flushToS3() {
        String s3Key = generateS3Key();
        s3Service.uploadJsonLogs(buffer, s3Key);
        buffer.clear();
        log.info("Flushed remaining logs to S3.");
    }

    @Override
    public synchronized void logAction(Long userId, UserAction action, Long itemId, Long categoryId, int price) {
        Map<String, Object> event = Map.of(
                "user_id", userId,
                "action", action,
                "item_id", itemId,
                "timestamp", System.currentTimeMillis(),
                "category_id", categoryId,
                "price", price
        );
        buffer.add(event);
        log.info("Current buffer: {}", buffer);
        if (buffer.size() >= MAX_EVENTS) {
            flushToS3();
        }
    }

    private String generateS3Key() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String prefix = "user-actions/";
        String datePath = now.format(dateFormatter) + "/";
        String uniqueFileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);

        return prefix + datePath + uniqueFileName + ".json";
    }
}