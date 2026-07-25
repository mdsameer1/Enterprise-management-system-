import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { UserDTO } from '../types/auth';

interface AuthState {
  user: UserDTO | null;
  token: string | null;
  refreshToken: string | null;
  isAuthenticated: boolean;
  setAuth: (user: UserDTO, token: string, refreshToken: string) => void;
  logout: () => void;
  updateUser: (user: UserDTO) => void;
}

export const useAuthStore = create<AuthState>(
  persist(
    (set) => ({
      user: null,
      token: null,
      refreshToken: null,
      isAuthenticated: false,
      setAuth: (user, token, refreshToken) =>
        set({
          user,
          token,
          refreshToken,
          isAuthenticated: true,
        }),
      logout: () =>
        set({
          user: null,
          token: null,
          refreshToken: null,
          isAuthenticated: false,
        }),
      updateUser: (user) => set({ user }),
    }),
    {
      name: 'auth-store',
    }
  )
);
