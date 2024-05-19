package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Competency} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetencyDTO implements Serializable {

    private Long id;

    private Integer competencyId;

    private String competencyName;

    @Lob
    private String description;

    private Set<EmployeeDTO> employees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompetencyId() {
        return competencyId;
    }

    public void setCompetencyId(Integer competencyId) {
        this.competencyId = competencyId;
    }

    public String getCompetencyName() {
        return competencyName;
    }

    public void setCompetencyName(String competencyName) {
        this.competencyName = competencyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof CompetencyDTO)) {
            return false;
        }

        CompetencyDTO competencyDTO = (CompetencyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, competencyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetencyDTO{" +
            "id=" + getId() +
            ", competencyId=" + getCompetencyId() +
            ", competencyName='" + getCompetencyName() + "'" +
            ", description='" + getDescription() + "'" +
            ", employees=" + getEmployees() +
            "}";
    }
}
