import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Department from './department';
import Employee from './employee';
import Competency from './competency';
import TrainingProgram from './training-program';
import EmployeeTraining from './employee-training';
import PerformanceReview from './performance-review';
import SkillSet from './skill-set';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="department/*" element={<Department />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="competency/*" element={<Competency />} />
        <Route path="training-program/*" element={<TrainingProgram />} />
        <Route path="employee-training/*" element={<EmployeeTraining />} />
        <Route path="performance-review/*" element={<PerformanceReview />} />
        <Route path="skill-set/*" element={<SkillSet />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
