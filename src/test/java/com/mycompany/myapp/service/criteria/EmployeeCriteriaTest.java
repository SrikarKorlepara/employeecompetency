package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmployeeCriteriaTest {

    @Test
    void newEmployeeCriteriaHasAllFiltersNullTest() {
        var employeeCriteria = new EmployeeCriteria();
        assertThat(employeeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void employeeCriteriaFluentMethodsCreatesFiltersTest() {
        var employeeCriteria = new EmployeeCriteria();

        setAllFilters(employeeCriteria);

        assertThat(employeeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void employeeCriteriaCopyCreatesNullFilterTest() {
        var employeeCriteria = new EmployeeCriteria();
        var copy = employeeCriteria.copy();

        assertThat(employeeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(employeeCriteria)
        );
    }

    @Test
    void employeeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var employeeCriteria = new EmployeeCriteria();
        setAllFilters(employeeCriteria);

        var copy = employeeCriteria.copy();

        assertThat(employeeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(employeeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var employeeCriteria = new EmployeeCriteria();

        assertThat(employeeCriteria).hasToString("EmployeeCriteria{}");
    }

    private static void setAllFilters(EmployeeCriteria employeeCriteria) {
        employeeCriteria.id();
        employeeCriteria.employeeId();
        employeeCriteria.firstName();
        employeeCriteria.lastName();
        employeeCriteria.email();
        employeeCriteria.phone();
        employeeCriteria.position();
        employeeCriteria.dateOfJoining();
        employeeCriteria.status();
        employeeCriteria.employeeTrainingId();
        employeeCriteria.performanceReviewId();
        employeeCriteria.reviewerId();
        employeeCriteria.skillSetId();
        employeeCriteria.competencyId();
        employeeCriteria.departmentId();
        employeeCriteria.distinct();
    }

    private static Condition<EmployeeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEmployeeId()) &&
                condition.apply(criteria.getFirstName()) &&
                condition.apply(criteria.getLastName()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getPhone()) &&
                condition.apply(criteria.getPosition()) &&
                condition.apply(criteria.getDateOfJoining()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getEmployeeTrainingId()) &&
                condition.apply(criteria.getPerformanceReviewId()) &&
                condition.apply(criteria.getReviewerId()) &&
                condition.apply(criteria.getSkillSetId()) &&
                condition.apply(criteria.getCompetencyId()) &&
                condition.apply(criteria.getDepartmentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmployeeCriteria> copyFiltersAre(EmployeeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEmployeeId(), copy.getEmployeeId()) &&
                condition.apply(criteria.getFirstName(), copy.getFirstName()) &&
                condition.apply(criteria.getLastName(), copy.getLastName()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getPhone(), copy.getPhone()) &&
                condition.apply(criteria.getPosition(), copy.getPosition()) &&
                condition.apply(criteria.getDateOfJoining(), copy.getDateOfJoining()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getEmployeeTrainingId(), copy.getEmployeeTrainingId()) &&
                condition.apply(criteria.getPerformanceReviewId(), copy.getPerformanceReviewId()) &&
                condition.apply(criteria.getReviewerId(), copy.getReviewerId()) &&
                condition.apply(criteria.getSkillSetId(), copy.getSkillSetId()) &&
                condition.apply(criteria.getCompetencyId(), copy.getCompetencyId()) &&
                condition.apply(criteria.getDepartmentId(), copy.getDepartmentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
