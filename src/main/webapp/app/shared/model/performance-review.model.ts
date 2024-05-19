import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IPerformanceReview {
  id?: number;
  reviewId?: number | null;
  reviewDate?: dayjs.Dayjs | null;
  comments?: string | null;
  overallRating?: string | null;
  employee?: IEmployee | null;
  reviewer?: IEmployee | null;
}

export const defaultValue: Readonly<IPerformanceReview> = {};
