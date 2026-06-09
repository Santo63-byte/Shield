package com.sbyte.shield.jobs;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sbyte.shield.core.base.impl.ShieldJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExpiredBlackListRemovalJob extends ShieldJob {

    @Value("${shield.jwt.access-token-ttl-seconds}")
    private long accessTokenTtlSeconds;

    @Override
    @Scheduled(cron = "${shield.job.blacklist.cleanup.period}")
    public void executeOnProActive() {
        log.info("Scheduled job triggered: ExpiredBlackListRemovalJob");
        fire();
    }

    @Override
    public void perform() {
        log.info("Starting expired blacklist token cleanup job");
        jobRepository.deleteBlacklistedTokensOlderThan(getCutoffTime());
    }

    public LocalDateTime getCutoffTime() {
        return LocalDateTime.now().minusSeconds(accessTokenTtlSeconds);
    }
    @Override
    @EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
    public void executeOnApplicationReady() {
        log.info("Executing ExpiredBlackListRemovalJob on application startup");
        fire();
    }
}
