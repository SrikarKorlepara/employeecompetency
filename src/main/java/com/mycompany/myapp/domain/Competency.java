package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Competency.
 */
@Entity
@Table(name = "competency")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Competency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "competency_id")
    private Integer competencyId;

    @Column(name = "competency_name")
    private String competencyName;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "competencies")
    @JsonIgnoreProperties(
        value = { "employeeTrainings", "performanceReviews", "reviewers", "skillSets", "competencies", "department" },
        allowSetters = true
    )
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Competency id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompetencyId() {
        return this.competencyId;
    }

    public Competency competencyId(Integer competencyId) {
        this.setCompetencyId(competencyId);
        return this;
    }

    public void setCompetencyId(Integer competencyId) {
        this.competencyId = competencyId;
    }

    public String getCompetencyName() {
        return this.competencyName;
    }

    public Competency competencyName(String competencyName) {
        this.setCompetencyName(competencyName);
        return this;
    }

    public void setCompetencyName(String competencyName) {
        this.competencyName = competencyName;
    }

    public String getDescription() {
        return this.description;
    }

    public Competency description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.removeCompetency(this));
        }
        if (employees != null) {
            employees.forEach(i -> i.addCompetency(this));
        }
        this.employees = employees;
    }

    public Competency employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Competency addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getCompetencies().add(this);
        return this;
    }

    public Competency removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getCompetencies().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competency)) {
            return false;
        }
        return getId() != null && getId().equals(((Competency) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competency{" +
            "id=" + getId() +
            ", competencyId=" + getCompetencyId() +
            ", competencyName='" + getCompetencyName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
