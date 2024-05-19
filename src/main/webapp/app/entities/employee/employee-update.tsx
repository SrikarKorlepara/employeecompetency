import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISkillSet } from 'app/shared/model/skill-set.model';
import { getEntities as getSkillSets } from 'app/entities/skill-set/skill-set.reducer';
import { ICompetency } from 'app/shared/model/competency.model';
import { getEntities as getCompetencies } from 'app/entities/competency/competency.reducer';
import { IDepartment } from 'app/shared/model/department.model';
import { getEntities as getDepartments } from 'app/entities/department/department.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeePosition } from 'app/shared/model/enumerations/employee-position.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';

export const EmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const skillSets = useAppSelector(state => state.skillSet.entities);
  const competencies = useAppSelector(state => state.competency.entities);
  const departments = useAppSelector(state => state.department.entities);
  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);
  const employeePositionValues = Object.keys(EmployeePosition);
  const employeeStatusValues = Object.keys(EmployeeStatus);

  const handleClose = () => {
    navigate('/employee' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSkillSets({}));
    dispatch(getCompetencies({}));
    dispatch(getDepartments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.employeeId !== undefined && typeof values.employeeId !== 'number') {
      values.employeeId = Number(values.employeeId);
    }

    const entity = {
      ...employeeEntity,
      ...values,
      skillSets: mapIdList(values.skillSets),
      competencies: mapIdList(values.competencies),
      department: departments.find(it => it.id.toString() === values.department?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          position: 'SOFTWARE_ENGINEER',
          status: 'ACTIVE',
          ...employeeEntity,
          skillSets: employeeEntity?.skillSets?.map(e => e.id.toString()),
          competencies: employeeEntity?.competencies?.map(e => e.id.toString()),
          department: employeeEntity?.department?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="employeeinfoApp.employee.home.createOrEditLabel" data-cy="EmployeeCreateUpdateHeading">
            <Translate contentKey="employeeinfoApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="employee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('employeeinfoApp.employee.employeeId')}
                id="employee-employeeId"
                name="employeeId"
                data-cy="employeeId"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.employee.firstName')}
                id="employee-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.employee.lastName')}
                id="employee-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.employee.email')}
                id="employee-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.employee.phone')}
                id="employee-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.employee.position')}
                id="employee-position"
                name="position"
                data-cy="position"
                type="select"
              >
                {employeePositionValues.map(employeePosition => (
                  <option value={employeePosition} key={employeePosition}>
                    {translate('employeeinfoApp.EmployeePosition.' + employeePosition)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('employeeinfoApp.employee.dateOfJoining')}
                id="employee-dateOfJoining"
                name="dateOfJoining"
                data-cy="dateOfJoining"
                type="date"
              />
              <ValidatedField
                label={translate('employeeinfoApp.employee.status')}
                id="employee-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {employeeStatusValues.map(employeeStatus => (
                  <option value={employeeStatus} key={employeeStatus}>
                    {translate('employeeinfoApp.EmployeeStatus.' + employeeStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('employeeinfoApp.employee.skillSet')}
                id="employee-skillSet"
                data-cy="skillSet"
                type="select"
                multiple
                name="skillSets"
              >
                <option value="" key="0" />
                {skillSets
                  ? skillSets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('employeeinfoApp.employee.competency')}
                id="employee-competency"
                data-cy="competency"
                type="select"
                multiple
                name="competencies"
              >
                <option value="" key="0" />
                {competencies
                  ? competencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.competencyName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-department"
                name="department"
                data-cy="department"
                label={translate('employeeinfoApp.employee.department')}
                type="select"
              >
                <option value="" key="0" />
                {departments
                  ? departments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.departmentName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EmployeeUpdate;
