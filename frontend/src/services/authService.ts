import api from './api';
import { LoginRequest, LoginResponse, RegisterRequest, UserDTO } from '../types/auth';

export const authService = {
  login: (credentials: LoginRequest) =>
    api.post<{ data: LoginResponse }>('/auth/login', credentials),

  register: (data: RegisterRequest) =>
    api.post<{ data: UserDTO }>('/auth/register', data),

  refreshToken: (refreshToken: string) =>
    api.post<{ data: LoginResponse }>('/auth/refresh', {}, {
      headers: { Authorization: `Bearer ${refreshToken}` },
    }),

  logout: () => Promise.resolve(),
};
