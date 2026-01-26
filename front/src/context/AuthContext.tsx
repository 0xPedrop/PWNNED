import React, { createContext, useState, useEffect, useCallback } from "react";
import { api } from "@/lib/api";

interface LoginCredentials {
  email: string;
  password: string;
}

export interface AuthContextType {
  isAuthenticated: boolean;
  isLoading: boolean;
  user: any | null;
  login: (credentials: LoginCredentials) => Promise<void>;
  logout: () => void;
  checkAuth: () => Promise<boolean>;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [user, setUser] = useState<any | null>(null);

  const checkAuth = useCallback(async (): Promise<boolean> => {
    try {
      const response = await api.get("/auth/validate");
      console.log("Dados do usuário carregados:", response.data); // DEBUG IMPORTANTE
      setIsAuthenticated(true);
      setUser(response.data);
      return true;
    } catch (error) {
      setIsAuthenticated(false);
      setUser(null);
      return false;
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    checkAuth();
  }, [checkAuth]);

  const login = async (credentials: LoginCredentials) => {
    try {
      await api.post("/auth/login", {
        email: credentials.email,
        password: credentials.password
      });
      
      const isValid = await checkAuth();
      if (!isValid) {
        throw new Error("Sessão não pôde ser validada.");
      }
    } catch (error) {
      setIsAuthenticated(false);
      throw error;
    }
  };

  const logout = async () => {
    try {
      await api.post("/auth/logout");
    } catch (error) {
      console.error("Erro ao fazer logout", error);
    } finally {
      setIsAuthenticated(false);
      setUser(null);
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, isLoading, user, login, logout, checkAuth }}>
      {children}
    </AuthContext.Provider>
  );
};