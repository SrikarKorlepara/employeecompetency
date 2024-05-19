import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Competency from './competency';
import CompetencyDetail from './competency-detail';
import CompetencyUpdate from './competency-update';
import CompetencyDeleteDialog from './competency-delete-dialog';

const CompetencyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Competency />} />
    <Route path="new" element={<CompetencyUpdate />} />
    <Route path=":id">
      <Route index element={<CompetencyDetail />} />
      <Route path="edit" element={<CompetencyUpdate />} />
      <Route path="delete" element={<CompetencyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompetencyRoutes;
