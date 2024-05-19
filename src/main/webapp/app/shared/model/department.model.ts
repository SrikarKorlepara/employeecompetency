export interface IDepartment {
  id?: number;
  departmentId?: number | null;
  departmentName?: string | null;
}

export const defaultValue: Readonly<IDepartment> = {};
