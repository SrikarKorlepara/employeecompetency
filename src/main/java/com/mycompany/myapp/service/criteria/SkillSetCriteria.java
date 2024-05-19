package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.ProficiencyLevel;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.SkillSet} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.SkillSetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skill-sets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkillSetCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ProficiencyLevel
     */
    public static class ProficiencyLevelFilter extends Filter<ProficiencyLevel> {

        public ProficiencyLevelFilter() {}

        public ProficiencyLevelFilter(ProficiencyLevelFilter filter) {
            super(filter);
        }

        @Override
        public ProficiencyLevelFilter copy() {
            return new ProficiencyLevelFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ProficiencyLevelFilter profieciencyLevel;

    private LocalDateFilter lastAssessedDate;

    private LongFilter employeeId;

    private Boolean distinct;

    public SkillSetCriteria() {}

    public SkillSetCriteria(SkillSetCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.profieciencyLevel = other.optionalProfieciencyLevel().map(ProficiencyLevelFilter::copy).orElse(null);
        this.lastAssessedDate = other.optionalLastAssessedDate().map(LocalDateFilter::copy).orElse(null);
        this.employeeId = other.optionalEmployeeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SkillSetCriteria copy() {
        return new SkillSetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ProficiencyLevelFilter getProfieciencyLevel() {
        return profieciencyLevel;
    }

    public Optional<ProficiencyLevelFilter> optionalProfieciencyLevel() {
        return Optional.ofNullable(profieciencyLevel);
    }

    public ProficiencyLevelFilter profieciencyLevel() {
        if (profieciencyLevel == null) {
            setProfieciencyLevel(new ProficiencyLevelFilter());
        }
        return profieciencyLevel;
    }

    public void setProfieciencyLevel(ProficiencyLevelFilter profieciencyLevel) {
        this.profieciencyLevel = profieciencyLevel;
    }

    public LocalDateFilter getLastAssessedDate() {
        return lastAssessedDate;
    }

    public Optional<LocalDateFilter> optionalLastAssessedDate() {
        return Optional.ofNullable(lastAssessedDate);
    }

    public LocalDateFilter lastAssessedDate() {
        if (lastAssessedDate == null) {
            setLastAssessedDate(new LocalDateFilter());
        }
        return lastAssessedDate;
    }

    public void setLastAssessedDate(LocalDateFilter lastAssessedDate) {
        this.lastAssessedDate = lastAssessedDate;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public Optional<LongFilter> optionalEmployeeId() {
        return Optional.ofNullable(employeeId);
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            setEmployeeId(new LongFilter());
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillSetCriteria that = (SkillSetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(profieciencyLevel, that.profieciencyLevel) &&
            Objects.equals(lastAssessedDate, that.lastAssessedDate) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, profieciencyLevel, lastAssessedDate, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillSetCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalProfieciencyLevel().map(f -> "profieciencyLevel=" + f + ", ").orElse("") +
            optionalLastAssessedDate().map(f -> "lastAssessedDate=" + f + ", ").orElse("") +
            optionalEmployeeId().map(f -> "employeeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
