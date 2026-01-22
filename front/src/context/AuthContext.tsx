import { createContext, useContext, useState, useEffect, ReactNode } from "react";
import { api } from "@/lib/api"; 

// Define o tipo dos dados que o login espera
interface LoginCredentials {
  email: string;
  password: string;
}

// Atualiza a interface do Contexto
interface AuthContextType {
  isAuthenticated: boolean;
  login: (credentials: LoginCredentials) => Promise<void>;
  logout: () => Promise<void>;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkSession = async () => {
      try {
        // Verifica se o cookie de sessão ainda é válido no backend
        await api.get("/users/me"); // Ajuste conforme o endpoint de perfil real
        setIsAuthenticated(true);
      } catch (error) {
        setIsAuthenticated(false);
      } finally {
        setIsLoading(false);
      }
    };
    checkSession();
  }, []);

  const login = async (credentials: LoginCredentials) => {
    // IMPORTANTE: O Spring Security (formLogin) espera dados como formulário, não JSON
    const params = new URLSearchParams();
    params.append('username', credentials.email); // Spring padrão busca 'username'
    params.append('password', credentials.password);

    await api.post("/login", params, {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    });
    
    setIsAuthenticated(true);
  };

  const logout = async () => {
    try {
      await api.post("/logout");
      setIsAuthenticated(false);
    } catch (error) {
      console.error("Erro ao fazer logout", error);
      setIsAuthenticated(false);
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth deve ser usado dentro de um AuthProvider");
  }
  return context;
};