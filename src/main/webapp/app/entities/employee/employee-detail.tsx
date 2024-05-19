import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">
          <Translate contentKey="employeeinfoApp.employee.detail.title">Employee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <span id="employeeId">
              <Translate contentKey="employeeinfoApp.employee.employeeId">Employee Id</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.employeeId}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="employeeinfoApp.employee.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="employeeinfoApp.employee.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="employeeinfoApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="employeeinfoApp.employee.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.phone}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="employeeinfoApp.employee.position">Position</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.position}</dd>
          <dt>
            <span id="dateOfJoining">
              <Translate contentKey="employeeinfoApp.employee.dateOfJoining">Date Of Joining</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.dateOfJoining ? (
              <TextFormat value={employeeEntity.dateOfJoining} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="employeeinfoApp.employee.status">Status</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.status}</dd>
          <dt>
            <Translate contentKey="employeeinfoApp.employee.skillSet">Skill Set</Translate>
          </dt>
          <dd>
            {employeeEntity.skillSets
              ? employeeEntity.skillSets.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {employeeEntity.skillSets && i === employeeEntity.skillSets.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="employeeinfoApp.employee.competency">Competency</Translate>
          </dt>
          <dd>
            {employeeEntity.competencies
              ? employeeEntity.competencies.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.competencyName}</a>
                    {employeeEntity.competencies && i === employeeEntity.competencies.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="employeeinfoApp.employee.department">Department</Translate>
          </dt>
          <dd>{employeeEntity.department ? employeeEntity.department.departmentName : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;
