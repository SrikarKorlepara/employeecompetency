package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.PerformanceReview} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PerformanceReviewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /performance-reviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceReviewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter reviewId;

    private LocalDateFilter reviewDate;

    private StringFilter overallRating;

    private LongFilter employeeId;

    private LongFilter reviewerId;

    private Boolean distinct;

    public PerformanceReviewCriteria() {}

    public PerformanceReviewCriteria(PerformanceReviewCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.reviewId = other.optionalReviewId().map(IntegerFilter::copy).orElse(null);
        this.reviewDate = other.optionalReviewDate().map(LocalDateFilter::copy).orElse(null);
        this.overallRating = other.optionalOverallRating().map(StringFilter::copy).orElse(null);
        this.employeeId = other.optionalEmployeeId().map(LongFilter::copy).orElse(null);
        this.reviewerId = other.optionalReviewerId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PerformanceReviewCriteria copy() {
        return new PerformanceReviewCriteria(this);
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

    public IntegerFilter getReviewId() {
        return reviewId;
    }

    public Optional<IntegerFilter> optionalReviewId() {
        return Optional.ofNullable(reviewId);
    }

    public IntegerFilter reviewId() {
        if (reviewId == null) {
            setReviewId(new IntegerFilter());
        }
        return reviewId;
    }

    public void setReviewId(IntegerFilter reviewId) {
        this.reviewId = reviewId;
    }

    public LocalDateFilter getReviewDate() {
        return reviewDate;
    }

    public Optional<LocalDateFilter> optionalReviewDate() {
        return Optional.ofNullable(reviewDate);
    }

    public LocalDateFilter reviewDate() {
        if (reviewDate == null) {
            setReviewDate(new LocalDateFilter());
        }
        return reviewDate;
    }

    public void setReviewDate(LocalDateFilter reviewDate) {
        this.reviewDate = reviewDate;
    }

    public StringFilter getOverallRating() {
        return overallRating;
    }

    public Optional<StringFilter> optionalOverallRating() {
        return Optional.ofNullable(overallRating);
    }

    public StringFilter overallRating() {
        if (overallRating == null) {
            setOverallRating(new StringFilter());
        }
        return overallRating;
    }

    public void setOverallRating(StringFilter overallRating) {
        this.overallRating = overallRating;
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

    public LongFilter getReviewerId() {
        return reviewerId;
    }

    public Optional<LongFilter> optionalReviewerId() {
        return Optional.ofNullable(reviewerId);
    }

    public LongFilter reviewerId() {
        if (reviewerId == null) {
            setReviewerId(new LongFilter());
        }
        return reviewerId;
    }

    public void setReviewerId(LongFilter reviewerId) {
        this.reviewerId = reviewerId;
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
        final PerformanceReviewCriteria that = (PerformanceReviewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(reviewId, that.reviewId) &&
            Objects.equals(reviewDate, that.reviewDate) &&
            Objects.equals(overallRating, that.overallRating) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(reviewerId, that.reviewerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reviewId, reviewDate, overallRating, employeeId, reviewerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceReviewCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalReviewId().map(f -> "reviewId=" + f + ", ").orElse("") +
            optionalReviewDate().map(f -> "reviewDate=" + f + ", ").orElse("") +
            optionalOverallRating().map(f -> "overallRating=" + f + ", ").orElse("") +
            optionalEmployeeId().map(f -> "employeeId=" + f + ", ").orElse("") +
            optionalReviewerId().map(f -> "reviewerId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
