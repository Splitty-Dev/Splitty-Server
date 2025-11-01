package com.chaegangjo.scheduler;

import com.chaegangjo.logger.UserActionLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserActionFlushScheduler {

    private final UserActionLogger logger;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleFlush() {
        logger.flush();
    }
}