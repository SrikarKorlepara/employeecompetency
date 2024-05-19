package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SkillSetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SkillSet getSkillSetSample1() {
        return new SkillSet().id(1L).name("name1");
    }

    public static SkillSet getSkillSetSample2() {
        return new SkillSet().id(2L).name("name2");
    }

    public static SkillSet getSkillSetRandomSampleGenerator() {
        return new SkillSet().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
