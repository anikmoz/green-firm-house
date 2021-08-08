import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IProductType } from 'app/shared/model/product-type.model';
import { getEntities as getProductTypes } from 'app/entities/product-type/product-type.reducer';
import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { getEntity, updateEntity, createEntity, reset } from './customer-bought.reducer';
import { ICustomerBought } from 'app/shared/model/customer-bought.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomerBoughtUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const productTypes = useAppSelector(state => state.productType.entities);
  const customers = useAppSelector(state => state.customer.entities);
  const customerBoughtEntity = useAppSelector(state => state.customerBought.entity);
  const loading = useAppSelector(state => state.customerBought.loading);
  const updating = useAppSelector(state => state.customerBought.updating);
  const updateSuccess = useAppSelector(state => state.customerBought.updateSuccess);

  const handleClose = () => {
    props.history.push('/customer-bought' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProductTypes({}));
    dispatch(getCustomers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.deliveryDate = convertDateTimeToServer(values.deliveryDate);

    const entity = {
      ...customerBoughtEntity,
      ...values,
      productType: productTypes.find(it => it.id.toString() === values.productTypeId.toString()),
      customer: customers.find(it => it.id.toString() === values.customerId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          deliveryDate: displayDefaultDateTime(),
        }
      : {
          ...customerBoughtEntity,
          weightType: 'LITTRE',
          deliveryDate: convertDateTimeFromServer(customerBoughtEntity.deliveryDate),
          status: 'DUE',
          productTypeId: customerBoughtEntity?.productType?.id,
          customerId: customerBoughtEntity?.customer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="greenFirmHouseApp.customerBought.home.createOrEditLabel" data-cy="CustomerBoughtCreateUpdateHeading">
            Create or edit a CustomerBought
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
                <ValidatedField name="id" required readOnly id="customer-bought-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                id="customer-bought-productType"
                name="productTypeId"
                data-cy="productType"
                label="Product Type"
                type="select"
                required
              >
                <option value="" key="0" />
                {productTypes
                  ? productTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField label="Weight Type" id="customer-bought-weightType" name="weightType" data-cy="weightType" type="select">
                <option value="LITTRE">LITTRE</option>
                <option value="KG">KG</option>
                <option value="GRAM">GRAM</option>
              </ValidatedField>
              <ValidatedField
                label="Unit Price"
                id="customer-bought-unitPrice"
                name="unitPrice"
                data-cy="unitPrice"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Total Weight"
                id="customer-bought-totalWeight"
                name="totalWeight"
                data-cy="totalWeight"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Total Price"
                id="customer-bought-totalPrice"
                name="totalPrice"
                data-cy="totalPrice"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Status" id="customer-bought-status" name="status" data-cy="status" type="select">
                <option value="DUE">DUE</option>
                <option value="PAID">PAID</option>
              </ValidatedField>
              <ValidatedField
                label="Delivery Date"
                id="customer-bought-deliveryDate"
                name="deliveryDate"
                data-cy="deliveryDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Remarks" id="customer-bought-remarks" name="remarks" data-cy="remarks" type="text" />
              <ValidatedField id="customer-bought-customer" name="customerId" data-cy="customer" label="Customer" type="select" required>
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customer-bought" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CustomerBoughtUpdate;
