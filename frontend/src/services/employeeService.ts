import api from './api';
import { EmployeeDTO, PaginationDTO } from '../types';

export const employeeService = {
  getAll: (page = 0, size = 20) =>
    api.get<{ data: PaginationDTO<EmployeeDTO> }>('/employees', {
      params: { page, size, sort: 'id', direction: 'desc' },
    }),

  getById: (id: number) =>
    api.get<{ data: EmployeeDTO }>(`/employees/${id}`),

  create: (data: Partial<EmployeeDTO>) =>
    api.post<{ data: EmployeeDTO }>('/employees', data),

  update: (id: number, data: Partial<EmployeeDTO>) =>
    api.put<{ data: EmployeeDTO }>(`/employees/${id}`, data),

  delete: (id: number) =>
    api.delete(`/employees/${id}`),
};
