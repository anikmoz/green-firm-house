import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CustomerBought from './customer-bought';
import CustomerBoughtDetail from './customer-bought-detail';
import CustomerBoughtUpdate from './customer-bought-update';
import CustomerBoughtDeleteDialog from './customer-bought-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CustomerBoughtUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CustomerBoughtUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CustomerBoughtDetail} />
      <ErrorBoundaryRoute path={match.url} component={CustomerBought} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CustomerBoughtDeleteDialog} />
  </>
);

export default Routes;
