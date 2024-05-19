package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TrainingProgramTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TrainingProgram getTrainingProgramSample1() {
        return new TrainingProgram().id(1L).trainingId(1).trainingName("trainingName1");
    }

    public static TrainingProgram getTrainingProgramSample2() {
        return new TrainingProgram().id(2L).trainingId(2).trainingName("trainingName2");
    }

    public static TrainingProgram getTrainingProgramRandomSampleGenerator() {
        return new TrainingProgram()
            .id(longCount.incrementAndGet())
            .trainingId(intCount.incrementAndGet())
            .trainingName(UUID.randomUUID().toString());
    }
}
