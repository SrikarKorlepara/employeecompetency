import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './skill-set.reducer';

export const SkillSetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const skillSetEntity = useAppSelector(state => state.skillSet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="skillSetDetailsHeading">
          <Translate contentKey="employeeinfoApp.skillSet.detail.title">SkillSet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{skillSetEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="employeeinfoApp.skillSet.name">Name</Translate>
            </span>
          </dt>
          <dd>{skillSetEntity.name}</dd>
          <dt>
            <span id="profieciencyLevel">
              <Translate contentKey="employeeinfoApp.skillSet.profieciencyLevel">Profieciency Level</Translate>
            </span>
          </dt>
          <dd>{skillSetEntity.profieciencyLevel}</dd>
          <dt>
            <span id="lastAssessedDate">
              <Translate contentKey="employeeinfoApp.skillSet.lastAssessedDate">Last Assessed Date</Translate>
            </span>
          </dt>
          <dd>
            {skillSetEntity.lastAssessedDate ? (
              <TextFormat value={skillSetEntity.lastAssessedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="employeeinfoApp.skillSet.employee">Employee</Translate>
          </dt>
          <dd>
            {skillSetEntity.employees
              ? skillSetEntity.employees.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.employeeId}</a>
                    {skillSetEntity.employees && i === skillSetEntity.employees.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/skill-set" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/skill-set/${skillSetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SkillSetDetail;
