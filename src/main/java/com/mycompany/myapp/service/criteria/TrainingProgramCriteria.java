package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.TrainingProgram} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TrainingProgramResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /training-programs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainingProgramCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter trainingId;

    private StringFilter trainingName;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private Boolean distinct;

    public TrainingProgramCriteria() {}

    public TrainingProgramCriteria(TrainingProgramCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.trainingId = other.optionalTrainingId().map(IntegerFilter::copy).orElse(null);
        this.trainingName = other.optionalTrainingName().map(StringFilter::copy).orElse(null);
        this.startDate = other.optionalStartDate().map(LocalDateFilter::copy).orElse(null);
        this.endDate = other.optionalEndDate().map(LocalDateFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TrainingProgramCriteria copy() {
        return new TrainingProgramCriteria(this);
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

    public IntegerFilter getTrainingId() {
        return trainingId;
    }

    public Optional<IntegerFilter> optionalTrainingId() {
        return Optional.ofNullable(trainingId);
    }

    public IntegerFilter trainingId() {
        if (trainingId == null) {
            setTrainingId(new IntegerFilter());
        }
        return trainingId;
    }

    public void setTrainingId(IntegerFilter trainingId) {
        this.trainingId = trainingId;
    }

    public StringFilter getTrainingName() {
        return trainingName;
    }

    public Optional<StringFilter> optionalTrainingName() {
        return Optional.ofNullable(trainingName);
    }

    public StringFilter trainingName() {
        if (trainingName == null) {
            setTrainingName(new StringFilter());
        }
        return trainingName;
    }

    public void setTrainingName(StringFilter trainingName) {
        this.trainingName = trainingName;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public Optional<LocalDateFilter> optionalStartDate() {
        return Optional.ofNullable(startDate);
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            setStartDate(new LocalDateFilter());
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public Optional<LocalDateFilter> optionalEndDate() {
        return Optional.ofNullable(endDate);
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            setEndDate(new LocalDateFilter());
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
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
        final TrainingProgramCriteria that = (TrainingProgramCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(trainingName, that.trainingName) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingId, trainingName, startDate, endDate, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingProgramCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTrainingId().map(f -> "trainingId=" + f + ", ").orElse("") +
            optionalTrainingName().map(f -> "trainingName=" + f + ", ").orElse("") +
            optionalStartDate().map(f -> "startDate=" + f + ", ").orElse("") +
            optionalEndDate().map(f -> "endDate=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
