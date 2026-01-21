import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/context/AuthContext";
import { Loader2 } from "lucide-react"; 

const ProtectedRoute = () => {
  const { isAuthenticated, isLoading } = useAuth();

  // 1. Enquanto verifica a sessão (chamada simulada ao /me), mostra loading
  if (isLoading) {
    return (
      <div className="flex h-screen items-center justify-center bg-background">
        <Loader2 className="h-8 w-8 animate-spin text-primary" />
      </div>
    );
  }

  // 2. Se logado, renderiza a rota filha (Outlet). Se não, chuta pro Login.
  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;