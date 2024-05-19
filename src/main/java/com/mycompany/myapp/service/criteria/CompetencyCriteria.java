package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Competency} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompetencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /competencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter competencyId;

    private StringFilter competencyName;

    private LongFilter employeeId;

    private Boolean distinct;

    public CompetencyCriteria() {}

    public CompetencyCriteria(CompetencyCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.competencyId = other.optionalCompetencyId().map(IntegerFilter::copy).orElse(null);
        this.competencyName = other.optionalCompetencyName().map(StringFilter::copy).orElse(null);
        this.employeeId = other.optionalEmployeeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CompetencyCriteria copy() {
        return new CompetencyCriteria(this);
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

    public IntegerFilter getCompetencyId() {
        return competencyId;
    }

    public Optional<IntegerFilter> optionalCompetencyId() {
        return Optional.ofNullable(competencyId);
    }

    public IntegerFilter competencyId() {
        if (competencyId == null) {
            setCompetencyId(new IntegerFilter());
        }
        return competencyId;
    }

    public void setCompetencyId(IntegerFilter competencyId) {
        this.competencyId = competencyId;
    }

    public StringFilter getCompetencyName() {
        return competencyName;
    }

    public Optional<StringFilter> optionalCompetencyName() {
        return Optional.ofNullable(competencyName);
    }

    public StringFilter competencyName() {
        if (competencyName == null) {
            setCompetencyName(new StringFilter());
        }
        return competencyName;
    }

    public void setCompetencyName(StringFilter competencyName) {
        this.competencyName = competencyName;
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
        final CompetencyCriteria that = (CompetencyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(competencyId, that.competencyId) &&
            Objects.equals(competencyName, that.competencyName) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, competencyId, competencyName, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetencyCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCompetencyId().map(f -> "competencyId=" + f + ", ").orElse("") +
            optionalCompetencyName().map(f -> "competencyName=" + f + ", ").orElse("") +
            optionalEmployeeId().map(f -> "employeeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
