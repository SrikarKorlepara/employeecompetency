import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrainingProgram } from 'app/shared/model/training-program.model';
import { getEntity, updateEntity, createEntity, reset } from './training-program.reducer';

export const TrainingProgramUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const trainingProgramEntity = useAppSelector(state => state.trainingProgram.entity);
  const loading = useAppSelector(state => state.trainingProgram.loading);
  const updating = useAppSelector(state => state.trainingProgram.updating);
  const updateSuccess = useAppSelector(state => state.trainingProgram.updateSuccess);

  const handleClose = () => {
    navigate('/training-program' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.trainingId !== undefined && typeof values.trainingId !== 'number') {
      values.trainingId = Number(values.trainingId);
    }

    const entity = {
      ...trainingProgramEntity,
      ...values,
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
          ...trainingProgramEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="employeeinfoApp.trainingProgram.home.createOrEditLabel" data-cy="TrainingProgramCreateUpdateHeading">
            <Translate contentKey="employeeinfoApp.trainingProgram.home.createOrEditLabel">Create or edit a TrainingProgram</Translate>
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
                  id="training-program-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('employeeinfoApp.trainingProgram.trainingId')}
                id="training-program-trainingId"
                name="trainingId"
                data-cy="trainingId"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.trainingProgram.trainingName')}
                id="training-program-trainingName"
                name="trainingName"
                data-cy="trainingName"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.trainingProgram.description')}
                id="training-program-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('employeeinfoApp.trainingProgram.startDate')}
                id="training-program-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('employeeinfoApp.trainingProgram.endDate')}
                id="training-program-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/training-program" replace color="info">
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

export default TrainingProgramUpdate;
