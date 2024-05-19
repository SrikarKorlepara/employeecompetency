import { IEmployee } from 'app/shared/model/employee.model';

export interface ICompetency {
  id?: number;
  competencyId?: number | null;
  competencyName?: string | null;
  description?: string | null;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<ICompetency> = {};
