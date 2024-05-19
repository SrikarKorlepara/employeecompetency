package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.ProficiencyLevel;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A SkillSet.
 */
@Entity
@Table(name = "skill_set")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkillSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "profieciency_level")
    private ProficiencyLevel profieciencyLevel;

    @Column(name = "last_assessed_date")
    private LocalDate lastAssessedDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skillSets")
    @JsonIgnoreProperties(
        value = { "employeeTrainings", "performanceReviews", "reviewers", "skillSets", "competencies", "department" },
        allowSetters = true
    )
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SkillSet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SkillSet name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProficiencyLevel getProfieciencyLevel() {
        return this.profieciencyLevel;
    }

    public SkillSet profieciencyLevel(ProficiencyLevel profieciencyLevel) {
        this.setProfieciencyLevel(profieciencyLevel);
        return this;
    }

    public void setProfieciencyLevel(ProficiencyLevel profieciencyLevel) {
        this.profieciencyLevel = profieciencyLevel;
    }

    public LocalDate getLastAssessedDate() {
        return this.lastAssessedDate;
    }

    public SkillSet lastAssessedDate(LocalDate lastAssessedDate) {
        this.setLastAssessedDate(lastAssessedDate);
        return this;
    }

    public void setLastAssessedDate(LocalDate lastAssessedDate) {
        this.lastAssessedDate = lastAssessedDate;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.removeSkillSet(this));
        }
        if (employees != null) {
            employees.forEach(i -> i.addSkillSet(this));
        }
        this.employees = employees;
    }

    public SkillSet employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public SkillSet addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getSkillSets().add(this);
        return this;
    }

    public SkillSet removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getSkillSets().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillSet)) {
            return false;
        }
        return getId() != null && getId().equals(((SkillSet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillSet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", profieciencyLevel='" + getProfieciencyLevel() + "'" +
            ", lastAssessedDate='" + getLastAssessedDate() + "'" +
            "}";
    }
}
