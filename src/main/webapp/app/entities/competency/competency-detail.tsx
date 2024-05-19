import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './competency.reducer';

export const CompetencyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const competencyEntity = useAppSelector(state => state.competency.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="competencyDetailsHeading">
          <Translate contentKey="employeeinfoApp.competency.detail.title">Competency</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{competencyEntity.id}</dd>
          <dt>
            <span id="competencyId">
              <Translate contentKey="employeeinfoApp.competency.competencyId">Competency Id</Translate>
            </span>
          </dt>
          <dd>{competencyEntity.competencyId}</dd>
          <dt>
            <span id="competencyName">
              <Translate contentKey="employeeinfoApp.competency.competencyName">Competency Name</Translate>
            </span>
          </dt>
          <dd>{competencyEntity.competencyName}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="employeeinfoApp.competency.description">Description</Translate>
            </span>
          </dt>
          <dd>{competencyEntity.description}</dd>
          <dt>
            <Translate contentKey="employeeinfoApp.competency.employee">Employee</Translate>
          </dt>
          <dd>
            {competencyEntity.employees
              ? competencyEntity.employees.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.employeeId}</a>
                    {competencyEntity.employees && i === competencyEntity.employees.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/competency" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/competency/${competencyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompetencyDetail;
