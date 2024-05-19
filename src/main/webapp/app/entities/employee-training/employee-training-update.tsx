import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrainingProgram } from 'app/shared/model/training-program.model';
import { getEntities as getTrainingPrograms } from 'app/entities/training-program/training-program.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IEmployeeTraining } from 'app/shared/model/employee-training.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-training.reducer';

export const EmployeeTrainingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const trainingPrograms = useAppSelector(state => state.trainingProgram.entities);
  const employees = useAppSelector(state => state.employee.entities);
  const employeeTrainingEntity = useAppSelector(state => state.employeeTraining.entity);
  const loading = useAppSelector(state => state.employeeTraining.loading);
  const updating = useAppSelector(state => state.employeeTraining.updating);
  const updateSuccess = useAppSelector(state => state.employeeTraining.updateSuccess);

  const handleClose = () => {
    navigate('/employee-training' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTrainingPrograms({}));
    dispatch(getEmployees({}));
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

    const entity = {
      ...employeeTrainingEntity,
      ...values,
      trainingProgram: trainingPrograms.find(it => it.id.toString() === values.trainingProgram?.toString()),
      employee: employees.find(it => it.id.toString() === values.employee?.toString()),
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
          ...employeeTrainingEntity,
          trainingProgram: employeeTrainingEntity?.trainingProgram?.id,
          employee: employeeTrainingEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="employeeinfoApp.employeeTraining.home.createOrEditLabel" data-cy="EmployeeTrainingCreateUpdateHeading">
            <Translate contentKey="employeeinfoApp.employeeTraining.home.createOrEditLabel">Create or edit a EmployeeTraining</Translate>
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
                  id="employee-training-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('employeeinfoApp.employeeTraining.completionStatus')}
                id="employee-training-completionStatus"
                name="completionStatus"
                data-cy="completionStatus"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.employeeTraining.completionDate')}
                id="employee-training-completionDate"
                name="completionDate"
                data-cy="completionDate"
                type="date"
              />
              <ValidatedField
                id="employee-training-trainingProgram"
                name="trainingProgram"
                data-cy="trainingProgram"
                label={translate('employeeinfoApp.employeeTraining.trainingProgram')}
                type="select"
              >
                <option value="" key="0" />
                {trainingPrograms
                  ? trainingPrograms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-training-employee"
                name="employee"
                data-cy="employee"
                label={translate('employeeinfoApp.employeeTraining.employee')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-training" replace color="info">
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

export default EmployeeTrainingUpdate;
