import dayjs from 'dayjs';

export interface ITrainingProgram {
  id?: number;
  trainingId?: number | null;
  trainingName?: string | null;
  description?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<ITrainingProgram> = {};
