package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmployeeTrainingCriteriaTest {

    @Test
    void newEmployeeTrainingCriteriaHasAllFiltersNullTest() {
        var employeeTrainingCriteria = new EmployeeTrainingCriteria();
        assertThat(employeeTrainingCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void employeeTrainingCriteriaFluentMethodsCreatesFiltersTest() {
        var employeeTrainingCriteria = new EmployeeTrainingCriteria();

        setAllFilters(employeeTrainingCriteria);

        assertThat(employeeTrainingCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void employeeTrainingCriteriaCopyCreatesNullFilterTest() {
        var employeeTrainingCriteria = new EmployeeTrainingCriteria();
        var copy = employeeTrainingCriteria.copy();

        assertThat(employeeTrainingCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(employeeTrainingCriteria)
        );
    }

    @Test
    void employeeTrainingCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var employeeTrainingCriteria = new EmployeeTrainingCriteria();
        setAllFilters(employeeTrainingCriteria);

        var copy = employeeTrainingCriteria.copy();

        assertThat(employeeTrainingCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(employeeTrainingCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var employeeTrainingCriteria = new EmployeeTrainingCriteria();

        assertThat(employeeTrainingCriteria).hasToString("EmployeeTrainingCriteria{}");
    }

    private static void setAllFilters(EmployeeTrainingCriteria employeeTrainingCriteria) {
        employeeTrainingCriteria.id();
        employeeTrainingCriteria.completionStatus();
        employeeTrainingCriteria.completionDate();
        employeeTrainingCriteria.trainingProgramId();
        employeeTrainingCriteria.employeeId();
        employeeTrainingCriteria.distinct();
    }

    private static Condition<EmployeeTrainingCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCompletionStatus()) &&
                condition.apply(criteria.getCompletionDate()) &&
                condition.apply(criteria.getTrainingProgramId()) &&
                condition.apply(criteria.getEmployeeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmployeeTrainingCriteria> copyFiltersAre(
        EmployeeTrainingCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCompletionStatus(), copy.getCompletionStatus()) &&
                condition.apply(criteria.getCompletionDate(), copy.getCompletionDate()) &&
                condition.apply(criteria.getTrainingProgramId(), copy.getTrainingProgramId()) &&
                condition.apply(criteria.getEmployeeId(), copy.getEmployeeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
