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
import { IPerformanceReview } from 'app/shared/model/performance-review.model';
import { getEntity, updateEntity, createEntity, reset } from './performance-review.reducer';

export const PerformanceReviewUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const performanceReviewEntity = useAppSelector(state => state.performanceReview.entity);
  const loading = useAppSelector(state => state.performanceReview.loading);
  const updating = useAppSelector(state => state.performanceReview.updating);
  const updateSuccess = useAppSelector(state => state.performanceReview.updateSuccess);

  const handleClose = () => {
    navigate('/performance-review' + location.search);
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
    if (values.reviewId !== undefined && typeof values.reviewId !== 'number') {
      values.reviewId = Number(values.reviewId);
    }

    const entity = {
      ...performanceReviewEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee?.toString()),
      reviewer: employees.find(it => it.id.toString() === values.reviewer?.toString()),
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
          ...performanceReviewEntity,
          employee: performanceReviewEntity?.employee?.id,
          reviewer: performanceReviewEntity?.reviewer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="employeeinfoApp.performanceReview.home.createOrEditLabel" data-cy="PerformanceReviewCreateUpdateHeading">
            <Translate contentKey="employeeinfoApp.performanceReview.home.createOrEditLabel">Create or edit a PerformanceReview</Translate>
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
                  id="performance-review-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('employeeinfoApp.performanceReview.reviewId')}
                id="performance-review-reviewId"
                name="reviewId"
                data-cy="reviewId"
                type="text"
              />
              <ValidatedField
                label={translate('employeeinfoApp.performanceReview.reviewDate')}
                id="performance-review-reviewDate"
                name="reviewDate"
                data-cy="reviewDate"
                type="date"
              />
              <ValidatedField
                label={translate('employeeinfoApp.performanceReview.comments')}
                id="performance-review-comments"
                name="comments"
                data-cy="comments"
                type="textarea"
              />
              <ValidatedField
                label={translate('employeeinfoApp.performanceReview.overallRating')}
                id="performance-review-overallRating"
                name="overallRating"
                data-cy="overallRating"
                type="text"
              />
              <ValidatedField
                id="performance-review-employee"
                name="employee"
                data-cy="employee"
                label={translate('employeeinfoApp.performanceReview.employee')}
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
              <ValidatedField
                id="performance-review-reviewer"
                name="reviewer"
                data-cy="reviewer"
                label={translate('employeeinfoApp.performanceReview.reviewer')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/performance-review" replace color="info">
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

export default PerformanceReviewUpdate;
