package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PerformanceReview.
 */
@Entity
@Table(name = "performance_review")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "review_id")
    private Integer reviewId;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Column(name = "overall_rating")
    private String overallRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "employeeTrainings", "performanceReviews", "reviewers", "skillSets", "competencies", "department" },
        allowSetters = true
    )
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "employeeTrainings", "performanceReviews", "reviewers", "skillSets", "competencies", "department" },
        allowSetters = true
    )
    private Employee reviewer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerformanceReview id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReviewId() {
        return this.reviewId;
    }

    public PerformanceReview reviewId(Integer reviewId) {
        this.setReviewId(reviewId);
        return this;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public LocalDate getReviewDate() {
        return this.reviewDate;
    }

    public PerformanceReview reviewDate(LocalDate reviewDate) {
        this.setReviewDate(reviewDate);
        return this;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getComments() {
        return this.comments;
    }

    public PerformanceReview comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOverallRating() {
        return this.overallRating;
    }

    public PerformanceReview overallRating(String overallRating) {
        this.setOverallRating(overallRating);
        return this;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PerformanceReview employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public Employee getReviewer() {
        return this.reviewer;
    }

    public void setReviewer(Employee employee) {
        this.reviewer = employee;
    }

    public PerformanceReview reviewer(Employee employee) {
        this.setReviewer(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceReview)) {
            return false;
        }
        return getId() != null && getId().equals(((PerformanceReview) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceReview{" +
            "id=" + getId() +
            ", reviewId=" + getReviewId() +
            ", reviewDate='" + getReviewDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", overallRating='" + getOverallRating() + "'" +
            "}";
    }
}
