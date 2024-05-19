package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CompetencyCriteriaTest {

    @Test
    void newCompetencyCriteriaHasAllFiltersNullTest() {
        var competencyCriteria = new CompetencyCriteria();
        assertThat(competencyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void competencyCriteriaFluentMethodsCreatesFiltersTest() {
        var competencyCriteria = new CompetencyCriteria();

        setAllFilters(competencyCriteria);

        assertThat(competencyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void competencyCriteriaCopyCreatesNullFilterTest() {
        var competencyCriteria = new CompetencyCriteria();
        var copy = competencyCriteria.copy();

        assertThat(competencyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(competencyCriteria)
        );
    }

    @Test
    void competencyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var competencyCriteria = new CompetencyCriteria();
        setAllFilters(competencyCriteria);

        var copy = competencyCriteria.copy();

        assertThat(competencyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(competencyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var competencyCriteria = new CompetencyCriteria();

        assertThat(competencyCriteria).hasToString("CompetencyCriteria{}");
    }

    private static void setAllFilters(CompetencyCriteria competencyCriteria) {
        competencyCriteria.id();
        competencyCriteria.competencyId();
        competencyCriteria.competencyName();
        competencyCriteria.employeeId();
        competencyCriteria.distinct();
    }

    private static Condition<CompetencyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCompetencyId()) &&
                condition.apply(criteria.getCompetencyName()) &&
                condition.apply(criteria.getEmployeeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CompetencyCriteria> copyFiltersAre(CompetencyCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCompetencyId(), copy.getCompetencyId()) &&
                condition.apply(criteria.getCompetencyName(), copy.getCompetencyName()) &&
                condition.apply(criteria.getEmployeeId(), copy.getEmployeeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
