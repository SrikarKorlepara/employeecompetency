package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.EmployeeTraining} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmployeeTrainingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeTrainingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter completionStatus;

    private LocalDateFilter completionDate;

    private LongFilter trainingProgramId;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeTrainingCriteria() {}

    public EmployeeTrainingCriteria(EmployeeTrainingCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.completionStatus = other.optionalCompletionStatus().map(StringFilter::copy).orElse(null);
        this.completionDate = other.optionalCompletionDate().map(LocalDateFilter::copy).orElse(null);
        this.trainingProgramId = other.optionalTrainingProgramId().map(LongFilter::copy).orElse(null);
        this.employeeId = other.optionalEmployeeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeTrainingCriteria copy() {
        return new EmployeeTrainingCriteria(this);
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

    public StringFilter getCompletionStatus() {
        return completionStatus;
    }

    public Optional<StringFilter> optionalCompletionStatus() {
        return Optional.ofNullable(completionStatus);
    }

    public StringFilter completionStatus() {
        if (completionStatus == null) {
            setCompletionStatus(new StringFilter());
        }
        return completionStatus;
    }

    public void setCompletionStatus(StringFilter completionStatus) {
        this.completionStatus = completionStatus;
    }

    public LocalDateFilter getCompletionDate() {
        return completionDate;
    }

    public Optional<LocalDateFilter> optionalCompletionDate() {
        return Optional.ofNullable(completionDate);
    }

    public LocalDateFilter completionDate() {
        if (completionDate == null) {
            setCompletionDate(new LocalDateFilter());
        }
        return completionDate;
    }

    public void setCompletionDate(LocalDateFilter completionDate) {
        this.completionDate = completionDate;
    }

    public LongFilter getTrainingProgramId() {
        return trainingProgramId;
    }

    public Optional<LongFilter> optionalTrainingProgramId() {
        return Optional.ofNullable(trainingProgramId);
    }

    public LongFilter trainingProgramId() {
        if (trainingProgramId == null) {
            setTrainingProgramId(new LongFilter());
        }
        return trainingProgramId;
    }

    public void setTrainingProgramId(LongFilter trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
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
        final EmployeeTrainingCriteria that = (EmployeeTrainingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(completionStatus, that.completionStatus) &&
            Objects.equals(completionDate, that.completionDate) &&
            Objects.equals(trainingProgramId, that.trainingProgramId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, completionStatus, completionDate, trainingProgramId, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeTrainingCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCompletionStatus().map(f -> "completionStatus=" + f + ", ").orElse("") +
            optionalCompletionDate().map(f -> "completionDate=" + f + ", ").orElse("") +
            optionalTrainingProgramId().map(f -> "trainingProgramId=" + f + ", ").orElse("") +
            optionalEmployeeId().map(f -> "employeeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
