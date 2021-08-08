import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './customer.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSortState, JhiItemCount, JhiPagination, TextFormat } from 'react-jhipster';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { getEntities } from 'app/entities/customer-bought/customer-bought.reducer';

export const CustomerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const customerBoughtList = useAppSelector(state => state.customerBought.entities);
  const loading = useAppSelector(state => state.customerBought.loading);
  const totalItems = useAppSelector(state => state.customerBought.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
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
  }, [props.location.search]);

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

  const { match } = props;

  return (
    <Row className={'justify-content-center'}>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">Customer</h2>
        <table className="table">
          <tr className="jh-entity-details">
            <td>
              <span id="id">ID</span>
            </td>
            <td>{customerEntity.id}</td>
          </tr>

          <tr className="jh-entity-details">
            <td>
              <span id="name">Name</span>
            </td>
            <td>{customerEntity.name}</td>
          </tr>

          <tr className="jh-entity-details">
            <td>
              <span id="email">Email</span>
            </td>
            <td>{customerEntity.email}</td>
          </tr>

          <tr className="jh-entity-details">
            <td>
              <span id="phone">Phone</span>
            </td>
            <td>{customerEntity.phone}</td>
          </tr>

          <tr className="jh-entity-details">
            <td>
              <span id="address">Address</span>
            </td>
            <td>{customerEntity.address}</td>
          </tr>
        </table>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>

      <Col md={8}>
        <div>
          <h2 id="customer-bought-heading" data-cy="CustomerBoughtHeading">
            Customer Boughts
            <div className="d-flex justify-content-end">
              <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
                <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
              </Button>
              <Link
                to={`/customer-bought/${null}/edit/${customerEntity.id}`}
                className="btn btn-primary jh-create-entity"
                id="jh-create-entity"
                data-cy="entityCreateButton"
              >
                <FontAwesomeIcon icon="plus" />
                &nbsp; Create new Customer Bought
              </Link>
            </div>
          </h2>
          <div className="table-responsive">
            {customerBoughtList && customerBoughtList.length > 0 ? (
              <Table responsive>
                <thead>
                  <tr>
                    <th className="hand" onClick={sort('deliveryDate')}>
                      Delivery Date <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      Product Type <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={sort('weightType')}>
                      Weight Type <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={sort('unitPrice')}>
                      Unit Price <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={sort('totalWeight')}>
                      Total Weight <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={sort('totalPrice')}>
                      Total Price <FontAwesomeIcon icon="sort" />
                    </th>

                    <th className="hand" onClick={sort('remarks')}>
                      Remarks <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={sort('status')}>
                      Status <FontAwesomeIcon icon="sort" />
                    </th>

                    <th />
                  </tr>
                </thead>
                <tbody>
                  {customerBoughtList.map((customerBought, i) => (
                    <tr key={`entity-${i}`} data-cy="entityTable">
                      <td>
                        {customerBought.deliveryDate ? (
                          <TextFormat type="date" value={customerBought.deliveryDate} format={APP_DATE_FORMAT} />
                        ) : null}
                      </td>
                      <td>
                        {customerBought.productType ? (
                          <Link to={`product-type/${customerBought.productType.id}`}>{customerBought.productType.name}</Link>
                        ) : (
                          ''
                        )}
                      </td>
                      <td>{customerBought.weightType}</td>
                      <td>{customerBought.unitPrice}</td>
                      <td>{customerBought.totalWeight}</td>
                      <td>{customerBought.totalPrice}</td>
                      <td>{customerBought.remarks}</td>
                      <td>{customerBought.status}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${customerBought.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button
                            tag={Link}
                            to={`${match.url}/${customerBought.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            color="primary"
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button
                            tag={Link}
                            to={`${match.url}/${customerBought.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            color="danger"
                            size="sm"
                            data-cy="entityDeleteButton"
                          >
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              !loading && <div className="alert alert-warning">No Customer Boughts found</div>
            )}
          </div>
          {totalItems ? (
            <div className={customerBoughtList && customerBoughtList.length > 0 ? '' : 'd-none'}>
              <Row className="justify-content-center">
                <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
              </Row>
              <Row className="justify-content-center">
                <JhiPagination
                  activePage={paginationState.activePage}
                  onSelect={handlePagination}
                  maxButtons={5}
                  itemsPerPage={paginationState.itemsPerPage}
                  totalItems={totalItems}
                />
              </Row>
            </div>
          ) : (
            ''
          )}
        </div>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
