import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SkillSet from './skill-set';
import SkillSetDetail from './skill-set-detail';
import SkillSetUpdate from './skill-set-update';
import SkillSetDeleteDialog from './skill-set-delete-dialog';

const SkillSetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SkillSet />} />
    <Route path="new" element={<SkillSetUpdate />} />
    <Route path=":id">
      <Route index element={<SkillSetDetail />} />
      <Route path="edit" element={<SkillSetUpdate />} />
      <Route path="delete" element={<SkillSetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SkillSetRoutes;
