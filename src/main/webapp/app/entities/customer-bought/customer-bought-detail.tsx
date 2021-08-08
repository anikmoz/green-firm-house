import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './customer-bought.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomerBoughtDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customerBoughtEntity = useAppSelector(state => state.customerBought.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerBoughtDetailsHeading">CustomerBought</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{customerBoughtEntity.id}</dd>
          <dt>
            <span id="weightType">Weight Type</span>
          </dt>
          <dd>{customerBoughtEntity.weightType}</dd>
          <dt>
            <span id="unitPrice">Unit Price</span>
          </dt>
          <dd>{customerBoughtEntity.unitPrice}</dd>
          <dt>
            <span id="totalPrice">Total Price</span>
          </dt>
          <dd>{customerBoughtEntity.totalPrice}</dd>
          <dt>
            <span id="deliveryDate">Delivery Date</span>
          </dt>
          <dd>
            {customerBoughtEntity.deliveryDate ? (
              <TextFormat value={customerBoughtEntity.deliveryDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="remarks">Remarks</span>
          </dt>
          <dd>{customerBoughtEntity.remarks}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{customerBoughtEntity.status}</dd>
          <dt>
            <span id="totalWeight">Total Weight</span>
          </dt>
          <dd>{customerBoughtEntity.totalWeight}</dd>
          <dt>Product Type</dt>
          <dd>{customerBoughtEntity.productType ? customerBoughtEntity.productType.name : ''}</dd>
          <dt>Customer</dt>
          <dd>{customerBoughtEntity.customer ? customerBoughtEntity.customer.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer-bought" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer-bought/${customerBoughtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerBoughtDetail;
