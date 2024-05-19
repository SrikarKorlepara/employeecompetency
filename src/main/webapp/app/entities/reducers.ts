import department from 'app/entities/department/department.reducer';
import employee from 'app/entities/employee/employee.reducer';
import competency from 'app/entities/competency/competency.reducer';
import trainingProgram from 'app/entities/training-program/training-program.reducer';
import employeeTraining from 'app/entities/employee-training/employee-training.reducer';
import performanceReview from 'app/entities/performance-review/performance-review.reducer';
import skillSet from 'app/entities/skill-set/skill-set.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  department,
  employee,
  competency,
  trainingProgram,
  employeeTraining,
  performanceReview,
  skillSet,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
