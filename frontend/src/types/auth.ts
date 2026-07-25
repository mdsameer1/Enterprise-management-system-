export interface UserDTO {
  id: number;
  username: string;
  email: string;
  role: 'ADMIN' | 'HR' | 'MANAGER' | 'EMPLOYEE';
  enabled: boolean;
  locked: boolean;
  profilePictureUrl?: string;
  phoneNumber?: string;
  createdAt: string;
  updatedAt: string;
  lastLoginAt: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  user: UserDTO;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
}
