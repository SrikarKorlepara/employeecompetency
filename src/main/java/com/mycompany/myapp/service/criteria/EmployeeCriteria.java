package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.EmployeePosition;
import com.mycompany.myapp.domain.enumeration.EmployeeStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Employee} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EmployeePosition
     */
    public static class EmployeePositionFilter extends Filter<EmployeePosition> {

        public EmployeePositionFilter() {}

        public EmployeePositionFilter(EmployeePositionFilter filter) {
            super(filter);
        }

        @Override
        public EmployeePositionFilter copy() {
            return new EmployeePositionFilter(this);
        }
    }

    /**
     * Class for filtering EmployeeStatus
     */
    public static class EmployeeStatusFilter extends Filter<EmployeeStatus> {

        public EmployeeStatusFilter() {}

        public EmployeeStatusFilter(EmployeeStatusFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeStatusFilter copy() {
            return new EmployeeStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter employeeId;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private StringFilter phone;

    private EmployeePositionFilter position;

    private LocalDateFilter dateOfJoining;

    private EmployeeStatusFilter status;

    private LongFilter employeeTrainingId;

    private LongFilter performanceReviewId;

    private LongFilter reviewerId;

    private LongFilter skillSetId;

    private LongFilter competencyId;

    private LongFilter departmentId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.employeeId = other.optionalEmployeeId().map(IntegerFilter::copy).orElse(null);
        this.firstName = other.optionalFirstName().map(StringFilter::copy).orElse(null);
        this.lastName = other.optionalLastName().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.phone = other.optionalPhone().map(StringFilter::copy).orElse(null);
        this.position = other.optionalPosition().map(EmployeePositionFilter::copy).orElse(null);
        this.dateOfJoining = other.optionalDateOfJoining().map(LocalDateFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(EmployeeStatusFilter::copy).orElse(null);
        this.employeeTrainingId = other.optionalEmployeeTrainingId().map(LongFilter::copy).orElse(null);
        this.performanceReviewId = other.optionalPerformanceReviewId().map(LongFilter::copy).orElse(null);
        this.reviewerId = other.optionalReviewerId().map(LongFilter::copy).orElse(null);
        this.skillSetId = other.optionalSkillSetId().map(LongFilter::copy).orElse(null);
        this.competencyId = other.optionalCompetencyId().map(LongFilter::copy).orElse(null);
        this.departmentId = other.optionalDepartmentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getEmployeeId() {
        return employeeId;
    }

    public Optional<IntegerFilter> optionalEmployeeId() {
        return Optional.ofNullable(employeeId);
    }

    public IntegerFilter employeeId() {
        if (employeeId == null) {
            setEmployeeId(new IntegerFilter());
        }
        return employeeId;
    }

    public void setEmployeeId(IntegerFilter employeeId) {
        this.employeeId = employeeId;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public Optional<StringFilter> optionalFirstName() {
        return Optional.ofNullable(firstName);
    }

    public StringFilter firstName() {
        if (firstName == null) {
            setFirstName(new StringFilter());
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public Optional<StringFilter> optionalLastName() {
        return Optional.ofNullable(lastName);
    }

    public StringFilter lastName() {
        if (lastName == null) {
            setLastName(new StringFilter());
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public Optional<StringFilter> optionalPhone() {
        return Optional.ofNullable(phone);
    }

    public StringFilter phone() {
        if (phone == null) {
            setPhone(new StringFilter());
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public EmployeePositionFilter getPosition() {
        return position;
    }

    public Optional<EmployeePositionFilter> optionalPosition() {
        return Optional.ofNullable(position);
    }

    public EmployeePositionFilter position() {
        if (position == null) {
            setPosition(new EmployeePositionFilter());
        }
        return position;
    }

    public void setPosition(EmployeePositionFilter position) {
        this.position = position;
    }

    public LocalDateFilter getDateOfJoining() {
        return dateOfJoining;
    }

    public Optional<LocalDateFilter> optionalDateOfJoining() {
        return Optional.ofNullable(dateOfJoining);
    }

    public LocalDateFilter dateOfJoining() {
        if (dateOfJoining == null) {
            setDateOfJoining(new LocalDateFilter());
        }
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDateFilter dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public EmployeeStatusFilter getStatus() {
        return status;
    }

    public Optional<EmployeeStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public EmployeeStatusFilter status() {
        if (status == null) {
            setStatus(new EmployeeStatusFilter());
        }
        return status;
    }

    public void setStatus(EmployeeStatusFilter status) {
        this.status = status;
    }

    public LongFilter getEmployeeTrainingId() {
        return employeeTrainingId;
    }

    public Optional<LongFilter> optionalEmployeeTrainingId() {
        return Optional.ofNullable(employeeTrainingId);
    }

    public LongFilter employeeTrainingId() {
        if (employeeTrainingId == null) {
            setEmployeeTrainingId(new LongFilter());
        }
        return employeeTrainingId;
    }

    public void setEmployeeTrainingId(LongFilter employeeTrainingId) {
        this.employeeTrainingId = employeeTrainingId;
    }

    public LongFilter getPerformanceReviewId() {
        return performanceReviewId;
    }

    public Optional<LongFilter> optionalPerformanceReviewId() {
        return Optional.ofNullable(performanceReviewId);
    }

    public LongFilter performanceReviewId() {
        if (performanceReviewId == null) {
            setPerformanceReviewId(new LongFilter());
        }
        return performanceReviewId;
    }

    public void setPerformanceReviewId(LongFilter performanceReviewId) {
        this.performanceReviewId = performanceReviewId;
    }

    public LongFilter getReviewerId() {
        return reviewerId;
    }

    public Optional<LongFilter> optionalReviewerId() {
        return Optional.ofNullable(reviewerId);
    }

    public LongFilter reviewerId() {
        if (reviewerId == null) {
            setReviewerId(new LongFilter());
        }
        return reviewerId;
    }

    public void setReviewerId(LongFilter reviewerId) {
        this.reviewerId = reviewerId;
    }

    public LongFilter getSkillSetId() {
        return skillSetId;
    }

    public Optional<LongFilter> optionalSkillSetId() {
        return Optional.ofNullable(skillSetId);
    }

    public LongFilter skillSetId() {
        if (skillSetId == null) {
            setSkillSetId(new LongFilter());
        }
        return skillSetId;
    }

    public void setSkillSetId(LongFilter skillSetId) {
        this.skillSetId = skillSetId;
    }

    public LongFilter getCompetencyId() {
        return competencyId;
    }

    public Optional<LongFilter> optionalCompetencyId() {
        return Optional.ofNullable(competencyId);
    }

    public LongFilter competencyId() {
        if (competencyId == null) {
            setCompetencyId(new LongFilter());
        }
        return competencyId;
    }

    public void setCompetencyId(LongFilter competencyId) {
        this.competencyId = competencyId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public Optional<LongFilter> optionalDepartmentId() {
        return Optional.ofNullable(departmentId);
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            setDepartmentId(new LongFilter());
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(position, that.position) &&
            Objects.equals(dateOfJoining, that.dateOfJoining) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeeTrainingId, that.employeeTrainingId) &&
            Objects.equals(performanceReviewId, that.performanceReviewId) &&
            Objects.equals(reviewerId, that.reviewerId) &&
            Objects.equals(skillSetId, that.skillSetId) &&
            Objects.equals(competencyId, that.competencyId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            employeeId,
            firstName,
            lastName,
            email,
            phone,
            position,
            dateOfJoining,
            status,
            employeeTrainingId,
            performanceReviewId,
            reviewerId,
            skillSetId,
            competencyId,
            departmentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEmployeeId().map(f -> "employeeId=" + f + ", ").orElse("") +
            optionalFirstName().map(f -> "firstName=" + f + ", ").orElse("") +
            optionalLastName().map(f -> "lastName=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalPhone().map(f -> "phone=" + f + ", ").orElse("") +
            optionalPosition().map(f -> "position=" + f + ", ").orElse("") +
            optionalDateOfJoining().map(f -> "dateOfJoining=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalEmployeeTrainingId().map(f -> "employeeTrainingId=" + f + ", ").orElse("") +
            optionalPerformanceReviewId().map(f -> "performanceReviewId=" + f + ", ").orElse("") +
            optionalReviewerId().map(f -> "reviewerId=" + f + ", ").orElse("") +
            optionalSkillSetId().map(f -> "skillSetId=" + f + ", ").orElse("") +
            optionalCompetencyId().map(f -> "competencyId=" + f + ", ").orElse("") +
            optionalDepartmentId().map(f -> "departmentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
