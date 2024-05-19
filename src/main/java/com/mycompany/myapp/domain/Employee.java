package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EmployeePosition;
import com.mycompany.myapp.domain.enumeration.EmployeeStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private EmployeePosition position;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmployeeStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "trainingProgram", "employee" }, allowSetters = true)
    private Set<EmployeeTraining> employeeTrainings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "reviewer" }, allowSetters = true)
    private Set<PerformanceReview> performanceReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewer")
    @JsonIgnoreProperties(value = { "employee", "reviewer" }, allowSetters = true)
    private Set<PerformanceReview> reviewers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_employee__skill_set",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_set_id")
    )
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Set<SkillSet> skillSets = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_employee__competency",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "competency_id")
    )
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Set<Competency> competencies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return this.employeeId;
    }

    public Employee employeeId(Integer employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Employee phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EmployeePosition getPosition() {
        return this.position;
    }

    public Employee position(EmployeePosition position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public LocalDate getDateOfJoining() {
        return this.dateOfJoining;
    }

    public Employee dateOfJoining(LocalDate dateOfJoining) {
        this.setDateOfJoining(dateOfJoining);
        return this;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public EmployeeStatus getStatus() {
        return this.status;
    }

    public Employee status(EmployeeStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public Set<EmployeeTraining> getEmployeeTrainings() {
        return this.employeeTrainings;
    }

    public void setEmployeeTrainings(Set<EmployeeTraining> employeeTrainings) {
        if (this.employeeTrainings != null) {
            this.employeeTrainings.forEach(i -> i.setEmployee(null));
        }
        if (employeeTrainings != null) {
            employeeTrainings.forEach(i -> i.setEmployee(this));
        }
        this.employeeTrainings = employeeTrainings;
    }

    public Employee employeeTrainings(Set<EmployeeTraining> employeeTrainings) {
        this.setEmployeeTrainings(employeeTrainings);
        return this;
    }

    public Employee addEmployeeTraining(EmployeeTraining employeeTraining) {
        this.employeeTrainings.add(employeeTraining);
        employeeTraining.setEmployee(this);
        return this;
    }

    public Employee removeEmployeeTraining(EmployeeTraining employeeTraining) {
        this.employeeTrainings.remove(employeeTraining);
        employeeTraining.setEmployee(null);
        return this;
    }

    public Set<PerformanceReview> getPerformanceReviews() {
        return this.performanceReviews;
    }

    public void setPerformanceReviews(Set<PerformanceReview> performanceReviews) {
        if (this.performanceReviews != null) {
            this.performanceReviews.forEach(i -> i.setEmployee(null));
        }
        if (performanceReviews != null) {
            performanceReviews.forEach(i -> i.setEmployee(this));
        }
        this.performanceReviews = performanceReviews;
    }

    public Employee performanceReviews(Set<PerformanceReview> performanceReviews) {
        this.setPerformanceReviews(performanceReviews);
        return this;
    }

    public Employee addPerformanceReview(PerformanceReview performanceReview) {
        this.performanceReviews.add(performanceReview);
        performanceReview.setEmployee(this);
        return this;
    }

    public Employee removePerformanceReview(PerformanceReview performanceReview) {
        this.performanceReviews.remove(performanceReview);
        performanceReview.setEmployee(null);
        return this;
    }

    public Set<PerformanceReview> getReviewers() {
        return this.reviewers;
    }

    public void setReviewers(Set<PerformanceReview> performanceReviews) {
        if (this.reviewers != null) {
            this.reviewers.forEach(i -> i.setReviewer(null));
        }
        if (performanceReviews != null) {
            performanceReviews.forEach(i -> i.setReviewer(this));
        }
        this.reviewers = performanceReviews;
    }

    public Employee reviewers(Set<PerformanceReview> performanceReviews) {
        this.setReviewers(performanceReviews);
        return this;
    }

    public Employee addReviewer(PerformanceReview performanceReview) {
        this.reviewers.add(performanceReview);
        performanceReview.setReviewer(this);
        return this;
    }

    public Employee removeReviewer(PerformanceReview performanceReview) {
        this.reviewers.remove(performanceReview);
        performanceReview.setReviewer(null);
        return this;
    }

    public Set<SkillSet> getSkillSets() {
        return this.skillSets;
    }

    public void setSkillSets(Set<SkillSet> skillSets) {
        this.skillSets = skillSets;
    }

    public Employee skillSets(Set<SkillSet> skillSets) {
        this.setSkillSets(skillSets);
        return this;
    }

    public Employee addSkillSet(SkillSet skillSet) {
        this.skillSets.add(skillSet);
        return this;
    }

    public Employee removeSkillSet(SkillSet skillSet) {
        this.skillSets.remove(skillSet);
        return this;
    }

    public Set<Competency> getCompetencies() {
        return this.competencies;
    }

    public void setCompetencies(Set<Competency> competencies) {
        this.competencies = competencies;
    }

    public Employee competencies(Set<Competency> competencies) {
        this.setCompetencies(competencies);
        return this;
    }

    public Employee addCompetency(Competency competency) {
        this.competencies.add(competency);
        return this;
    }

    public Employee removeCompetency(Competency competency) {
        this.competencies.remove(competency);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            ", dateOfJoining='" + getDateOfJoining() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
