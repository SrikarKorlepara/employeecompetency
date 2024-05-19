package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ProficiencyLevel;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.SkillSet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkillSetDTO implements Serializable {

    private Long id;

    private String name;

    private ProficiencyLevel profieciencyLevel;

    private LocalDate lastAssessedDate;

    private Set<EmployeeDTO> employees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProficiencyLevel getProfieciencyLevel() {
        return profieciencyLevel;
    }

    public void setProfieciencyLevel(ProficiencyLevel profieciencyLevel) {
        this.profieciencyLevel = profieciencyLevel;
    }

    public LocalDate getLastAssessedDate() {
        return lastAssessedDate;
    }

    public void setLastAssessedDate(LocalDate lastAssessedDate) {
        this.lastAssessedDate = lastAssessedDate;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillSetDTO)) {
            return false;
        }

        SkillSetDTO skillSetDTO = (SkillSetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, skillSetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillSetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", profieciencyLevel='" + getProfieciencyLevel() + "'" +
            ", lastAssessedDate='" + getLastAssessedDate() + "'" +
            ", employees=" + getEmployees() +
            "}";
    }
}
