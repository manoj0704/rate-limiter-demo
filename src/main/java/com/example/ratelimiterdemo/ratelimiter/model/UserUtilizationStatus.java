package com.example.ratelimiterdemo.ratelimiter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.System.currentTimeMillis;

public class UserUtilizationStatus {

    private final int maxCallsAllowed;

    private final long timeInMillis;

    private final List<Long> requestTimestamps;

    private final ReadWriteLock lock;

    public UserUtilizationStatus(int maxCallsAllowed, TimeUnit timeUnit) {
        this.maxCallsAllowed = maxCallsAllowed;
        this.timeInMillis = timeUnit.toMillis(1);
        this.requestTimestamps = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock(true);
    }

    public boolean checkRate() {
        lock.readLock().lock();
        try {
            if (requestTimestamps.size() >= maxCallsAllowed) {
                return false;
            }
        } finally {
            lock.readLock().unlock();
        }

        lock.writeLock().lock();
        try {
            if (requestTimestamps.size() < maxCallsAllowed) {
                requestTimestamps.add(currentTimeMillis());
                return true;
            } else {
                return false;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeOldest() {
        long threshold = currentTimeMillis() - this.timeInMillis;
        lock.writeLock().lock();
        try {
            requestTimestamps.removeIf(timestamps -> timestamps < threshold);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
