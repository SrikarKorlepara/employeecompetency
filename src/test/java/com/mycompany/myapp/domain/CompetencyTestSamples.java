package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CompetencyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Competency getCompetencySample1() {
        return new Competency().id(1L).competencyId(1).competencyName("competencyName1");
    }

    public static Competency getCompetencySample2() {
        return new Competency().id(2L).competencyId(2).competencyName("competencyName2");
    }

    public static Competency getCompetencyRandomSampleGenerator() {
        return new Competency()
            .id(longCount.incrementAndGet())
            .competencyId(intCount.incrementAndGet())
            .competencyName(UUID.randomUUID().toString());
    }
}
