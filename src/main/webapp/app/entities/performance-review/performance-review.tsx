import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './performance-review.reducer';

export const PerformanceReview = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const performanceReviewList = useAppSelector(state => state.performanceReview.entities);
  const loading = useAppSelector(state => state.performanceReview.loading);
  const totalItems = useAppSelector(state => state.performanceReview.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="performance-review-heading" data-cy="PerformanceReviewHeading">
        <Translate contentKey="employeeinfoApp.performanceReview.home.title">Performance Reviews</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="employeeinfoApp.performanceReview.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/performance-review/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="employeeinfoApp.performanceReview.home.createLabel">Create new Performance Review</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {performanceReviewList && performanceReviewList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="employeeinfoApp.performanceReview.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('reviewId')}>
                  <Translate contentKey="employeeinfoApp.performanceReview.reviewId">Review Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('reviewId')} />
                </th>
                <th className="hand" onClick={sort('reviewDate')}>
                  <Translate contentKey="employeeinfoApp.performanceReview.reviewDate">Review Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('reviewDate')} />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="employeeinfoApp.performanceReview.comments">Comments</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('comments')} />
                </th>
                <th className="hand" onClick={sort('overallRating')}>
                  <Translate contentKey="employeeinfoApp.performanceReview.overallRating">Overall Rating</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('overallRating')} />
                </th>
                <th>
                  <Translate contentKey="employeeinfoApp.performanceReview.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="employeeinfoApp.performanceReview.reviewer">Reviewer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {performanceReviewList.map((performanceReview, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/performance-review/${performanceReview.id}`} color="link" size="sm">
                      {performanceReview.id}
                    </Button>
                  </td>
                  <td>{performanceReview.reviewId}</td>
                  <td>
                    {performanceReview.reviewDate ? (
                      <TextFormat type="date" value={performanceReview.reviewDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{performanceReview.comments}</td>
                  <td>{performanceReview.overallRating}</td>
                  <td>
                    {performanceReview.employee ? (
                      <Link to={`/employee/${performanceReview.employee.id}`}>{performanceReview.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {performanceReview.reviewer ? (
                      <Link to={`/employee/${performanceReview.reviewer.id}`}>{performanceReview.reviewer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/performance-review/${performanceReview.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/performance-review/${performanceReview.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/performance-review/${performanceReview.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="employeeinfoApp.performanceReview.home.notFound">No Performance Reviews found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={performanceReviewList && performanceReviewList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default PerformanceReview;
