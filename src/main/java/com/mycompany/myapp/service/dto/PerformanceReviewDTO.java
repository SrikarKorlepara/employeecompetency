package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PerformanceReview} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceReviewDTO implements Serializable {

    private Long id;

    private Integer reviewId;

    private LocalDate reviewDate;

    @Lob
    private String comments;

    private String overallRating;

    private EmployeeDTO employee;

    private EmployeeDTO reviewer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public EmployeeDTO getReviewer() {
        return reviewer;
    }

    public void setReviewer(EmployeeDTO reviewer) {
        this.reviewer = reviewer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceReviewDTO)) {
            return false;
        }

        PerformanceReviewDTO performanceReviewDTO = (PerformanceReviewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, performanceReviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceReviewDTO{" +
            "id=" + getId() +
            ", reviewId=" + getReviewId() +
            ", reviewDate='" + getReviewDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", overallRating='" + getOverallRating() + "'" +
            ", employee=" + getEmployee() +
            ", reviewer=" + getReviewer() +
            "}";
    }
}
