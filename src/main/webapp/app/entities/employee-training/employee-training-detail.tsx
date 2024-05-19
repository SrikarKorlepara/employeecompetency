import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-training.reducer';

export const EmployeeTrainingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeTrainingEntity = useAppSelector(state => state.employeeTraining.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeTrainingDetailsHeading">
          <Translate contentKey="employeeinfoApp.employeeTraining.detail.title">EmployeeTraining</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeeTrainingEntity.id}</dd>
          <dt>
            <span id="completionStatus">
              <Translate contentKey="employeeinfoApp.employeeTraining.completionStatus">Completion Status</Translate>
            </span>
          </dt>
          <dd>{employeeTrainingEntity.completionStatus}</dd>
          <dt>
            <span id="completionDate">
              <Translate contentKey="employeeinfoApp.employeeTraining.completionDate">Completion Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeTrainingEntity.completionDate ? (
              <TextFormat value={employeeTrainingEntity.completionDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="employeeinfoApp.employeeTraining.trainingProgram">Training Program</Translate>
          </dt>
          <dd>{employeeTrainingEntity.trainingProgram ? employeeTrainingEntity.trainingProgram.id : ''}</dd>
          <dt>
            <Translate contentKey="employeeinfoApp.employeeTraining.employee">Employee</Translate>
          </dt>
          <dd>{employeeTrainingEntity.employee ? employeeTrainingEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-training" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-training/${employeeTrainingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeTrainingDetail;
