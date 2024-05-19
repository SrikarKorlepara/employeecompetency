package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EmployeePosition;
import com.mycompany.myapp.domain.enumeration.EmployeeStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Employee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO implements Serializable {

    private Long id;

    private Integer employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private EmployeePosition position;

    private LocalDate dateOfJoining;

    private EmployeeStatus status;

    private Set<SkillSetDTO> skillSets = new HashSet<>();

    private Set<CompetencyDTO> competencies = new HashSet<>();

    private DepartmentDTO department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public Set<SkillSetDTO> getSkillSets() {
        return skillSets;
    }

    public void setSkillSets(Set<SkillSetDTO> skillSets) {
        this.skillSets = skillSets;
    }

    public Set<CompetencyDTO> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(Set<CompetencyDTO> competencies) {
        this.competencies = competencies;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            ", dateOfJoining='" + getDateOfJoining() + "'" +
            ", status='" + getStatus() + "'" +
            ", skillSets=" + getSkillSets() +
            ", competencies=" + getCompetencies() +
            ", department=" + getDepartment() +
            "}";
    }
}
