import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';
import { ProficiencyLevel } from 'app/shared/model/enumerations/proficiency-level.model';

export interface ISkillSet {
  id?: number;
  name?: string | null;
  profieciencyLevel?: keyof typeof ProficiencyLevel | null;
  lastAssessedDate?: dayjs.Dayjs | null;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<ISkillSet> = {};
