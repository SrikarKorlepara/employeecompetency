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
import { ISkillSet } from 'app/shared/model/skill-set.model';
import { ProficiencyLevel } from 'app/shared/model/enumerations/proficiency-level.model';
import { getEntity, updateEntity, createEntity, reset } from './skill-set.reducer';

export const SkillSetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const skillSetEntity = useAppSelector(state => state.skillSet.entity);
  const loading = useAppSelector(state => state.skillSet.loading);
  const updating = useAppSelector(state => state.skillSet.updating);
  const updateSuccess = useAppSelector(state => state.skillSet.updateSuccess);
  const proficiencyLevelValues = Object.keys(ProficiencyLevel);

  const handleClose = () => {
    navigate('/skill-set' + location.search);
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

    const entity = {
      ...skillSetEntity,
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
          profieciencyLevel: 'BEGINNER',
          ...skillSetEntity,
          employees: skillSetEntity?.employees?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="employeeinfoApp.skillSet.home.createOrEditLabel" data-cy="SkillSetCreateUpdateHeading">
            <Translate contentKey="employeeinfoApp.skillSet.home.createOrEditLabel">Create or edit a SkillSet</Translate>
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
                  id="skill-set-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('employeeinfoApp.skillSet.name')}
                id="skill-set-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.skillSet.profieciencyLevel')}
                id="skill-set-profieciencyLevel"
                name="profieciencyLevel"
                data-cy="profieciencyLevel"
                type="select"
              >
                {proficiencyLevelValues.map(proficiencyLevel => (
                  <option value={proficiencyLevel} key={proficiencyLevel}>
                    {translate('employeeinfoApp.ProficiencyLevel.' + proficiencyLevel)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('employeeinfoApp.skillSet.lastAssessedDate')}
                id="skill-set-lastAssessedDate"
                name="lastAssessedDate"
                data-cy="lastAssessedDate"
                type="date"
              />
              <ValidatedField
                label={translate('employeeinfoApp.skillSet.employee')}
                id="skill-set-employee"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/skill-set" replace color="info">
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

export default SkillSetUpdate;
