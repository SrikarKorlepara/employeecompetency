package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmployeeTraining.
 */
@Entity
@Table(name = "employee_training")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeTraining implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "completion_status")
    private String completionStatus;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingProgram trainingProgram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "employeeTrainings", "performanceReviews", "reviewers", "skillSets", "competencies", "department" },
        allowSetters = true
    )
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeTraining id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletionStatus() {
        return this.completionStatus;
    }

    public EmployeeTraining completionStatus(String completionStatus) {
        this.setCompletionStatus(completionStatus);
        return this;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    public LocalDate getCompletionDate() {
        return this.completionDate;
    }

    public EmployeeTraining completionDate(LocalDate completionDate) {
        this.setCompletionDate(completionDate);
        return this;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public TrainingProgram getTrainingProgram() {
        return this.trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public EmployeeTraining trainingProgram(TrainingProgram trainingProgram) {
        this.setTrainingProgram(trainingProgram);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeTraining employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeTraining)) {
            return false;
        }
        return getId() != null && getId().equals(((EmployeeTraining) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeTraining{" +
            "id=" + getId() +
            ", completionStatus='" + getCompletionStatus() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            "}";
    }
}
