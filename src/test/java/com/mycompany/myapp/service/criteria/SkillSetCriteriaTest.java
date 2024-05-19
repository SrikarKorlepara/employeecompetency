package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SkillSetCriteriaTest {

    @Test
    void newSkillSetCriteriaHasAllFiltersNullTest() {
        var skillSetCriteria = new SkillSetCriteria();
        assertThat(skillSetCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void skillSetCriteriaFluentMethodsCreatesFiltersTest() {
        var skillSetCriteria = new SkillSetCriteria();

        setAllFilters(skillSetCriteria);

        assertThat(skillSetCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void skillSetCriteriaCopyCreatesNullFilterTest() {
        var skillSetCriteria = new SkillSetCriteria();
        var copy = skillSetCriteria.copy();

        assertThat(skillSetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(skillSetCriteria)
        );
    }

    @Test
    void skillSetCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var skillSetCriteria = new SkillSetCriteria();
        setAllFilters(skillSetCriteria);

        var copy = skillSetCriteria.copy();

        assertThat(skillSetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(skillSetCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var skillSetCriteria = new SkillSetCriteria();

        assertThat(skillSetCriteria).hasToString("SkillSetCriteria{}");
    }

    private static void setAllFilters(SkillSetCriteria skillSetCriteria) {
        skillSetCriteria.id();
        skillSetCriteria.name();
        skillSetCriteria.profieciencyLevel();
        skillSetCriteria.lastAssessedDate();
        skillSetCriteria.employeeId();
        skillSetCriteria.distinct();
    }

    private static Condition<SkillSetCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getProfieciencyLevel()) &&
                condition.apply(criteria.getLastAssessedDate()) &&
                condition.apply(criteria.getEmployeeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SkillSetCriteria> copyFiltersAre(SkillSetCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getProfieciencyLevel(), copy.getProfieciencyLevel()) &&
                condition.apply(criteria.getLastAssessedDate(), copy.getLastAssessedDate()) &&
                condition.apply(criteria.getEmployeeId(), copy.getEmployeeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
