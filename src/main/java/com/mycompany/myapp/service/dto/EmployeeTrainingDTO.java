package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EmployeeTraining} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeTrainingDTO implements Serializable {

    private Long id;

    private String completionStatus;

    private LocalDate completionDate;

    private TrainingProgramDTO trainingProgram;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public TrainingProgramDTO getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgramDTO trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeTrainingDTO)) {
            return false;
        }

        EmployeeTrainingDTO employeeTrainingDTO = (EmployeeTrainingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeTrainingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeTrainingDTO{" +
            "id=" + getId() +
            ", completionStatus='" + getCompletionStatus() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", trainingProgram=" + getTrainingProgram() +
            ", employee=" + getEmployee() +
            "}";
    }
}
