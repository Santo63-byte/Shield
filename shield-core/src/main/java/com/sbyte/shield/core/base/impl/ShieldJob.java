package com.sbyte.shield.core.base.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbyte.shield.configurations.policy.ShieldPolicy;
import com.sbyte.shield.core.base.abst.JobBase;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.authenticator.support.AuthSupport;
import com.sbyte.shield.datasource.mybatis.JobRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("shieldJob")
public abstract class ShieldJob implements JobBase {

    private AtomicInteger JOB_COUNT = new AtomicInteger(0);

    @Autowired
    protected  AuthSupport authSupport;

    @Autowired
    protected JobRepository jobRepository;

    @Autowired
    private ShieldPolicy shieldPolicy;
    
    @Override
    public void fire() throws ShieldExceptions{
        int currentCount = JOB_COUNT.incrementAndGet();
        try {
            log.info("Starting job: {} - Current running jobs: {}", this.getClass().getSimpleName(), currentCount);
            perform();
            postExecute();
        } catch (ShieldExceptions ex) {
            // Log the exception with job details
            log.error("Error executing job: {} - {}", this.getClass().getSimpleName(), ex.getMessage());
            throw ex; // Rethrow to allow higher-level handling if needed
        } finally {
            log.info("Completed job: {} - Remaining running jobs: {}", this.getClass().getSimpleName(), JOB_COUNT.decrementAndGet());
        }
    }
    @Override
    public abstract void perform() throws ShieldExceptions;
    @Override
    public void postExecute() throws ShieldExceptions{};
    @Override
    public void executeOnProActive() throws ShieldExceptions{};
    @Override
    public void executeOnApplicationReady() throws ShieldExceptions{};
}
