package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TrainingProgramCriteriaTest {

    @Test
    void newTrainingProgramCriteriaHasAllFiltersNullTest() {
        var trainingProgramCriteria = new TrainingProgramCriteria();
        assertThat(trainingProgramCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void trainingProgramCriteriaFluentMethodsCreatesFiltersTest() {
        var trainingProgramCriteria = new TrainingProgramCriteria();

        setAllFilters(trainingProgramCriteria);

        assertThat(trainingProgramCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void trainingProgramCriteriaCopyCreatesNullFilterTest() {
        var trainingProgramCriteria = new TrainingProgramCriteria();
        var copy = trainingProgramCriteria.copy();

        assertThat(trainingProgramCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(trainingProgramCriteria)
        );
    }

    @Test
    void trainingProgramCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var trainingProgramCriteria = new TrainingProgramCriteria();
        setAllFilters(trainingProgramCriteria);

        var copy = trainingProgramCriteria.copy();

        assertThat(trainingProgramCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(trainingProgramCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var trainingProgramCriteria = new TrainingProgramCriteria();

        assertThat(trainingProgramCriteria).hasToString("TrainingProgramCriteria{}");
    }

    private static void setAllFilters(TrainingProgramCriteria trainingProgramCriteria) {
        trainingProgramCriteria.id();
        trainingProgramCriteria.trainingId();
        trainingProgramCriteria.trainingName();
        trainingProgramCriteria.startDate();
        trainingProgramCriteria.endDate();
        trainingProgramCriteria.distinct();
    }

    private static Condition<TrainingProgramCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTrainingId()) &&
                condition.apply(criteria.getTrainingName()) &&
                condition.apply(criteria.getStartDate()) &&
                condition.apply(criteria.getEndDate()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TrainingProgramCriteria> copyFiltersAre(
        TrainingProgramCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTrainingId(), copy.getTrainingId()) &&
                condition.apply(criteria.getTrainingName(), copy.getTrainingName()) &&
                condition.apply(criteria.getStartDate(), copy.getStartDate()) &&
                condition.apply(criteria.getEndDate(), copy.getEndDate()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
