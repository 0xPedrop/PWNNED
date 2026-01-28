import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Terminal, Menu, X } from "lucide-react";
import { useState } from "react";

const Navbar = ({ simple = false }) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="fixed top-0 left-0 right-0 z-50 glass border-b border-white/5">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          {/* Logo - Sempre visível */}
          <Link to="/" className="flex items-center gap-2 group">
            <div className="relative">
              <Terminal className="h-8 w-8 text-primary transition-all duration-300 group-hover:text-primary group-hover:drop-shadow-[0_0_10px_rgba(34,211,238,0.6)]" />
            </div>
            <span className="text-xl font-bold tracking-tight">
              <span className="text-primary">Pwn</span>
              <span className="text-foreground">ned</span>
            </span>
          </Link>

          {/* Renderiza o Menu Desktop APENAS se NÃO for o modo simples */}
          {!simple && (
            <div className="hidden md:flex items-center gap-8">
              <a href="#pricing" className="text-muted-foreground hover:text-primary transition-colors duration-300">
                Preços
              </a>
              <a href="#team" className="text-muted-foreground hover:text-primary transition-colors duration-300">
                Time
              </a>
              <Link to="/login">
                <Button variant="ghost" className="text-muted-foreground">
                  Entrar
                </Button>
              </Link>
              <Link to="/signup">
                <Button variant="glow">
                  Comece
                </Button>
              </Link>
            </div>
          )}

          {/* Renderiza o Botão Mobile APENAS se NÃO for o modo simples */}
          {!simple && (
            <button
              onClick={() => setIsOpen(!isOpen)}
              className="md:hidden text-foreground p-2"
            >
              {isOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
            </button>
          )}
        </div>

        {/* Menu Mobile Content */}
        {!simple && isOpen && (
          <div className="md:hidden py-4 border-t border-white/5 animate-fade-in">
            <div className="flex flex-col gap-4">
              <a href="#pricing" className="text-muted-foreground hover:text-primary transition-colors py-2">
                Preços
              </a>
              <a href="#team" className="text-muted-foreground hover:text-primary transition-colors py-2">
                Time
              </a>
              <Link to="/login" className="py-2">
                <Button variant="ghost" className="w-full justify-start">
                  Entrar
                </Button>
              </Link>
              <Link to="/signup">
                <Button variant="glow" className="w-full">
                  Comece
                </Button>
              </Link>
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;