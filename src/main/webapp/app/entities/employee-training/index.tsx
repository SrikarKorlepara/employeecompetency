import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeTraining from './employee-training';
import EmployeeTrainingDetail from './employee-training-detail';
import EmployeeTrainingUpdate from './employee-training-update';
import EmployeeTrainingDeleteDialog from './employee-training-delete-dialog';

const EmployeeTrainingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeTraining />} />
    <Route path="new" element={<EmployeeTrainingUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeTrainingDetail />} />
      <Route path="edit" element={<EmployeeTrainingUpdate />} />
      <Route path="delete" element={<EmployeeTrainingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeTrainingRoutes;
