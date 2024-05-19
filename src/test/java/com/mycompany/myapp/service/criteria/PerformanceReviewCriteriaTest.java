package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PerformanceReviewCriteriaTest {

    @Test
    void newPerformanceReviewCriteriaHasAllFiltersNullTest() {
        var performanceReviewCriteria = new PerformanceReviewCriteria();
        assertThat(performanceReviewCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void performanceReviewCriteriaFluentMethodsCreatesFiltersTest() {
        var performanceReviewCriteria = new PerformanceReviewCriteria();

        setAllFilters(performanceReviewCriteria);

        assertThat(performanceReviewCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void performanceReviewCriteriaCopyCreatesNullFilterTest() {
        var performanceReviewCriteria = new PerformanceReviewCriteria();
        var copy = performanceReviewCriteria.copy();

        assertThat(performanceReviewCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(performanceReviewCriteria)
        );
    }

    @Test
    void performanceReviewCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var performanceReviewCriteria = new PerformanceReviewCriteria();
        setAllFilters(performanceReviewCriteria);

        var copy = performanceReviewCriteria.copy();

        assertThat(performanceReviewCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(performanceReviewCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var performanceReviewCriteria = new PerformanceReviewCriteria();

        assertThat(performanceReviewCriteria).hasToString("PerformanceReviewCriteria{}");
    }

    private static void setAllFilters(PerformanceReviewCriteria performanceReviewCriteria) {
        performanceReviewCriteria.id();
        performanceReviewCriteria.reviewId();
        performanceReviewCriteria.reviewDate();
        performanceReviewCriteria.overallRating();
        performanceReviewCriteria.employeeId();
        performanceReviewCriteria.reviewerId();
        performanceReviewCriteria.distinct();
    }

    private static Condition<PerformanceReviewCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getReviewId()) &&
                condition.apply(criteria.getReviewDate()) &&
                condition.apply(criteria.getOverallRating()) &&
                condition.apply(criteria.getEmployeeId()) &&
                condition.apply(criteria.getReviewerId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PerformanceReviewCriteria> copyFiltersAre(
        PerformanceReviewCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getReviewId(), copy.getReviewId()) &&
                condition.apply(criteria.getReviewDate(), copy.getReviewDate()) &&
                condition.apply(criteria.getOverallRating(), copy.getOverallRating()) &&
                condition.apply(criteria.getEmployeeId(), copy.getEmployeeId()) &&
                condition.apply(criteria.getReviewerId(), copy.getReviewerId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
