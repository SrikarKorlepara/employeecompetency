package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PerformanceReviewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PerformanceReview getPerformanceReviewSample1() {
        return new PerformanceReview().id(1L).reviewId(1).overallRating("overallRating1");
    }

    public static PerformanceReview getPerformanceReviewSample2() {
        return new PerformanceReview().id(2L).reviewId(2).overallRating("overallRating2");
    }

    public static PerformanceReview getPerformanceReviewRandomSampleGenerator() {
        return new PerformanceReview()
            .id(longCount.incrementAndGet())
            .reviewId(intCount.incrementAndGet())
            .overallRating(UUID.randomUUID().toString());
    }
}
