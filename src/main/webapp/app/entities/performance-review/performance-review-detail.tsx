import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './performance-review.reducer';

export const PerformanceReviewDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const performanceReviewEntity = useAppSelector(state => state.performanceReview.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="performanceReviewDetailsHeading">
          <Translate contentKey="employeeinfoApp.performanceReview.detail.title">PerformanceReview</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{performanceReviewEntity.id}</dd>
          <dt>
            <span id="reviewId">
              <Translate contentKey="employeeinfoApp.performanceReview.reviewId">Review Id</Translate>
            </span>
          </dt>
          <dd>{performanceReviewEntity.reviewId}</dd>
          <dt>
            <span id="reviewDate">
              <Translate contentKey="employeeinfoApp.performanceReview.reviewDate">Review Date</Translate>
            </span>
          </dt>
          <dd>
            {performanceReviewEntity.reviewDate ? (
              <TextFormat value={performanceReviewEntity.reviewDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="employeeinfoApp.performanceReview.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{performanceReviewEntity.comments}</dd>
          <dt>
            <span id="overallRating">
              <Translate contentKey="employeeinfoApp.performanceReview.overallRating">Overall Rating</Translate>
            </span>
          </dt>
          <dd>{performanceReviewEntity.overallRating}</dd>
          <dt>
            <Translate contentKey="employeeinfoApp.performanceReview.employee">Employee</Translate>
          </dt>
          <dd>{performanceReviewEntity.employee ? performanceReviewEntity.employee.id : ''}</dd>
          <dt>
            <Translate contentKey="employeeinfoApp.performanceReview.reviewer">Reviewer</Translate>
          </dt>
          <dd>{performanceReviewEntity.reviewer ? performanceReviewEntity.reviewer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/performance-review" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/performance-review/${performanceReviewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PerformanceReviewDetail;
