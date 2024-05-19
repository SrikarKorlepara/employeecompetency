import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PerformanceReview from './performance-review';
import PerformanceReviewDetail from './performance-review-detail';
import PerformanceReviewUpdate from './performance-review-update';
import PerformanceReviewDeleteDialog from './performance-review-delete-dialog';

const PerformanceReviewRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PerformanceReview />} />
    <Route path="new" element={<PerformanceReviewUpdate />} />
    <Route path=":id">
      <Route index element={<PerformanceReviewDetail />} />
      <Route path="edit" element={<PerformanceReviewUpdate />} />
      <Route path="delete" element={<PerformanceReviewDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PerformanceReviewRoutes;
