import React, { createContext, useState, useEffect } from "react";
import { api } from "@/lib/api";

// Tipos (Podem ficar aqui ou em types/auth.ts)
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
}

// Criação do Contexto
export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [user, setUser] = useState<any | null>(null);

  const checkAuth = async (): Promise<boolean> => {
    try {
      const response = await api.get("/auth/validate");
      setIsAuthenticated(true);
      setUser(response.data);
      return true; // Sucesso
    } catch (error) {
      setIsAuthenticated(false);
      setUser(null);
      return false; // Falha
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    checkAuth();
  }, []);

  const login = async (credentials: LoginCredentials) => {
    try {
      // 1. Faz o POST do login (Gera o Cookie)
      await api.post("/auth/login", {
        email: credentials.email,
        password: credentials.password
      });
      
      // 2. Verifica imediatamente se o Cookie foi aceito e a sessão é válida
      const isValid = await checkAuth();
      
      // 3. Se não for válido (ex: erro de cookie ou endpoint 404), lança erro
      if (!isValid) {
        throw new Error("Sessão não pôde ser validada. Verifique Cookies ou Backend.");
      }

    } catch (error) {
      setIsAuthenticated(false);
      throw error; // Isso vai acionar o Toast de erro no Login.tsx
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
    <AuthContext.Provider value={{ isAuthenticated, isLoading, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};