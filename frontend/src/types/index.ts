import { UserDTO } from './auth';

export interface EmployeeDTO {
  id: number;
  employeeId: string;
  userId: number;
  firstName: string;
  lastName: string;
  designation: string;
  department: string;
  departmentId?: number;
  managerId?: number;
  phoneNumber?: string;
  address?: string;
  city?: string;
  state?: string;
  zipCode?: string;
  country?: string;
  dateOfBirth?: string;
  gender?: string;
  bloodGroup?: string;
  panNumber?: string;
  aadharNumber?: string;
  joinDate: string;
  employmentType: string;
  salary?: number;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface DepartmentDTO {
  id: number;
  name: string;
  code: string;
  description?: string;
  managerId?: number;
  managerName?: string;
  location?: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
  employeeCount?: number;
}

export interface ProjectDTO {
  id: number;
  projectCode: string;
  name: string;
  description?: string;
  managerId: number;
  managerName?: string;
  status: 'PLANNING' | 'ACTIVE' | 'ON_HOLD' | 'COMPLETED' | 'CANCELLED';
  startDate: string;
  endDate: string;
  dueDate: string;
  budget?: number;
  progress: number;
  priority?: string;
  createdAt: string;
  updatedAt: string;
  taskCount?: number;
  teamMemberCount?: number;
}

export interface TaskDTO {
  id: number;
  taskCode: string;
  title: string;
  description?: string;
  projectId: number;
  projectName?: string;
  assignedToId?: number;
  assignedToName?: string;
  startDate: string;
  dueDate: string;
  status: 'TODO' | 'IN_PROGRESS' | 'IN_REVIEW' | 'COMPLETED' | 'BLOCKED';
  priority?: string;
  progress: number;
  comments?: string;
  parentTaskId?: number;
  createdAt: string;
  updatedAt: string;
}

export interface PaginationDTO<T> {
  content: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
}
