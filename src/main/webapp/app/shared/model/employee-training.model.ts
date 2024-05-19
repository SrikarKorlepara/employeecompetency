import dayjs from 'dayjs';
import { ITrainingProgram } from 'app/shared/model/training-program.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeTraining {
  id?: number;
  completionStatus?: string | null;
  completionDate?: dayjs.Dayjs | null;
  trainingProgram?: ITrainingProgram | null;
  employee?: IEmployee | null;
}

export const defaultValue: Readonly<IEmployeeTraining> = {};
