import dayjs from 'dayjs';
import { ISkillSet } from 'app/shared/model/skill-set.model';
import { ICompetency } from 'app/shared/model/competency.model';
import { IDepartment } from 'app/shared/model/department.model';
import { EmployeePosition } from 'app/shared/model/enumerations/employee-position.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

export interface IEmployee {
  id?: number;
  employeeId?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phone?: string | null;
  position?: keyof typeof EmployeePosition | null;
  dateOfJoining?: dayjs.Dayjs | null;
  status?: keyof typeof EmployeeStatus | null;
  skillSets?: ISkillSet[] | null;
  competencies?: ICompetency[] | null;
  department?: IDepartment | null;
}

export const defaultValue: Readonly<IEmployee> = {};
