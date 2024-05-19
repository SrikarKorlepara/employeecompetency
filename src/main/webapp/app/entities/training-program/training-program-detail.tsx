import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './training-program.reducer';

export const TrainingProgramDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trainingProgramEntity = useAppSelector(state => state.trainingProgram.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trainingProgramDetailsHeading">
          <Translate contentKey="employeeinfoApp.trainingProgram.detail.title">TrainingProgram</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trainingProgramEntity.id}</dd>
          <dt>
            <span id="trainingId">
              <Translate contentKey="employeeinfoApp.trainingProgram.trainingId">Training Id</Translate>
            </span>
          </dt>
          <dd>{trainingProgramEntity.trainingId}</dd>
          <dt>
            <span id="trainingName">
              <Translate contentKey="employeeinfoApp.trainingProgram.trainingName">Training Name</Translate>
            </span>
          </dt>
          <dd>{trainingProgramEntity.trainingName}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="employeeinfoApp.trainingProgram.description">Description</Translate>
            </span>
          </dt>
          <dd>{trainingProgramEntity.description}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="employeeinfoApp.trainingProgram.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {trainingProgramEntity.startDate ? (
              <TextFormat value={trainingProgramEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="employeeinfoApp.trainingProgram.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {trainingProgramEntity.endDate ? (
              <TextFormat value={trainingProgramEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/training-program" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/training-program/${trainingProgramEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrainingProgramDetail;
