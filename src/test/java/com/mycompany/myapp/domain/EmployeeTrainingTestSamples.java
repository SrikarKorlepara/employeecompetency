package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeeTrainingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EmployeeTraining getEmployeeTrainingSample1() {
        return new EmployeeTraining().id(1L).completionStatus("completionStatus1");
    }

    public static EmployeeTraining getEmployeeTrainingSample2() {
        return new EmployeeTraining().id(2L).completionStatus("completionStatus2");
    }

    public static EmployeeTraining getEmployeeTrainingRandomSampleGenerator() {
        return new EmployeeTraining().id(longCount.incrementAndGet()).completionStatus(UUID.randomUUID().toString());
    }
}
