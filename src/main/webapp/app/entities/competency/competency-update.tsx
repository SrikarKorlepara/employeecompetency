import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { ICompetency } from 'app/shared/model/competency.model';
import { getEntity, updateEntity, createEntity, reset } from './competency.reducer';

export const CompetencyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const competencyEntity = useAppSelector(state => state.competency.entity);
  const loading = useAppSelector(state => state.competency.loading);
  const updating = useAppSelector(state => state.competency.updating);
  const updateSuccess = useAppSelector(state => state.competency.updateSuccess);

  const handleClose = () => {
    navigate('/competency' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    if (values.competencyId !== undefined && typeof values.competencyId !== 'number') {
      values.competencyId = Number(values.competencyId);
    }

    const entity = {
      ...competencyEntity,
      ...values,
      employees: mapIdList(values.employees),
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
          ...competencyEntity,
          employees: competencyEntity?.employees?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="employeeinfoApp.competency.home.createOrEditLabel" data-cy="CompetencyCreateUpdateHeading">
            <Translate contentKey="employeeinfoApp.competency.home.createOrEditLabel">Create or edit a Competency</Translate>
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
                  id="competency-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('employeeinfoApp.competency.competencyId')}
                id="competency-competencyId"
                name="competencyId"
                data-cy="competencyId"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.competency.competencyName')}
                id="competency-competencyName"
                name="competencyName"
                data-cy="competencyName"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.competency.description')}
                id="competency-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('employeeinfoApp.competency.employee')}
                id="competency-employee"
                data-cy="employee"
                type="select"
                multiple
                name="employees"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.employeeId}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/competency" replace color="info">
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

export default CompetencyUpdate;
