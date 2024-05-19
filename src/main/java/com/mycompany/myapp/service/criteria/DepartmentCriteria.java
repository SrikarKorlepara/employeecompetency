package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Department} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DepartmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter departmentId;

    private StringFilter departmentName;

    private LongFilter employeeId;

    private Boolean distinct;

    public DepartmentCriteria() {}

    public DepartmentCriteria(DepartmentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.departmentId = other.optionalDepartmentId().map(IntegerFilter::copy).orElse(null);
        this.departmentName = other.optionalDepartmentName().map(StringFilter::copy).orElse(null);
        this.employeeId = other.optionalEmployeeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DepartmentCriteria copy() {
        return new DepartmentCriteria(this);
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

    public IntegerFilter getDepartmentId() {
        return departmentId;
    }

    public Optional<IntegerFilter> optionalDepartmentId() {
        return Optional.ofNullable(departmentId);
    }

    public IntegerFilter departmentId() {
        if (departmentId == null) {
            setDepartmentId(new IntegerFilter());
        }
        return departmentId;
    }

    public void setDepartmentId(IntegerFilter departmentId) {
        this.departmentId = departmentId;
    }

    public StringFilter getDepartmentName() {
        return departmentName;
    }

    public Optional<StringFilter> optionalDepartmentName() {
        return Optional.ofNullable(departmentName);
    }

    public StringFilter departmentName() {
        if (departmentName == null) {
            setDepartmentName(new StringFilter());
        }
        return departmentName;
    }

    public void setDepartmentName(StringFilter departmentName) {
        this.departmentName = departmentName;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public Optional<LongFilter> optionalEmployeeId() {
        return Optional.ofNullable(employeeId);
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            setEmployeeId(new LongFilter());
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final DepartmentCriteria that = (DepartmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(departmentName, that.departmentName) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departmentId, departmentName, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDepartmentId().map(f -> "departmentId=" + f + ", ").orElse("") +
            optionalDepartmentName().map(f -> "departmentName=" + f + ", ").orElse("") +
            optionalEmployeeId().map(f -> "employeeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
