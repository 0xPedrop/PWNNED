import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Terminal, Eye, EyeOff, ArrowRight, Loader2 } from "lucide-react";
import MatrixBackground from "@/components/MatrixBackground";
import { useAuth } from "@/hooks/useAuth";
import { useToast } from "@/hooks/use-toast";

const loginSchema = z.object({
  email: z
    .string()
    .min(1, "O e-mail é obrigatório")
    .email("Formato de email inválido")
    .trim(), 
  password: z
    .string()
    .min(8, "A senha deve ter no mínimo 8 caracteres"),  
});

type LoginFormValues = z.infer<typeof loginSchema>;

const Login = () => {
  const navigate = useNavigate();
  const { login } = useAuth();
  const { toast } = useToast();
  
  const [isExpanded, setIsExpanded] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    mode: "onChange",
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const onSubmit = async (data: LoginFormValues) => {
    try {
      await login({ 
        email: data.email as string, 
        password: data.password as string 
      });
      
      toast({
        title: "Access Concedido",
        description: "Bem vindo de volta ao mainframe",
      });
      navigate("/dashboard");
    } catch (error) {
      console.error(error);
      toast({
        variant: "destructive",
        title: "Acesso Negado",
        description: "Credenciais inválidas ou servidor inacessível.",
      });
    }
  };

  return (
    <div className="min-h-screen bg-background flex items-center justify-center p-4">
      <MatrixBackground />
      
      <div className={`relative z-10 w-full max-w-md transition-all duration-500 ease-in-out ${
        isExpanded ? 'scale-100' : 'scale-95'
      }`}>
        <div className="glass-card overflow-hidden shadow-2xl border border-white/10 rounded-lg backdrop-blur-md bg-black/40">
          
          {/* Terminal Header */}
          <div className="flex items-center gap-2 px-4 py-3 bg-muted/50 border-b border-white/5">
            <div className="flex gap-2">
              <div className="w-3 h-3 rounded-full bg-destructive/70" />
              <div className="w-3 h-3 rounded-full bg-yellow-500/70" />
              <div className="w-3 h-3 rounded-full bg-green-500/70" />
            </div>
            <span className="text-sm text-muted-foreground font-mono ml-2">
              {isExpanded ? 'pwnned@auth:~' : 'pwnned@root:~'}
            </span>
          </div>

          <div className="p-6 transition-all duration-500">
            {!isExpanded ? (
              /* ESTADO FECHADO */
              <div className="py-8">
                <button
                  onClick={() => setIsExpanded(true)}
                  className="w-full text-left font-mono text-sm group focus:outline-none"
                >
                  <div className="flex items-center gap-2 p-4 rounded bg-black/20 hover:bg-black/40 border border-transparent hover:border-primary/30 transition-all">
                    <span className="text-primary font-bold">root@pwnned</span>
                    <span className="text-muted-foreground">:</span>
                    <span className="text-secondary">~</span>
                    <span className="text-muted-foreground"># </span>
                    <span className="text-green-400 group-hover:text-green-300 transition-colors">
                      ./login.sh
                    </span>
                    <span className="inline-block w-2 h-4 bg-primary animate-pulse ml-1" />
                  </div>
                  <p className="text-xs text-muted-foreground mt-3 text-center opacity-70">
                    Execute login script to authenticate...
                  </p>
                </button>
              </div>
            ) : (
              /* ESTADO ABERTO */
              <div className="animate-in fade-in zoom-in-95 duration-300">
                <Link to="/" className="flex items-center justify-center gap-2 mb-6">
                  <Terminal className="h-8 w-8 text-primary" />
                  <span className="text-2xl font-bold">
                    <span className="text-primary">Pwn</span>
                    <span className="text-foreground">ned</span>
                  </span>
                </Link>

                <div className="font-mono text-sm mb-6 pb-4 border-b border-white/5">
                  <span className="text-primary">root@pwnned</span>
                  <span className="text-muted-foreground">:</span>
                  <span className="text-secondary">~</span>
                  <span className="text-muted-foreground"># </span>
                  <span className="text-foreground">./login.sh</span>
                </div>

                <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                  
                  {/* Campo Email */}
                  <div className="space-y-2">
                    <label className="text-sm font-mono text-muted-foreground">
                      {'>'} email:
                    </label>
                    <Input
                      type="email"
                      {...register("email")}
                      placeholder="hacker@pwnned.io"
                      maxLength={100}
                      className="font-mono bg-black/20 border-white/10 focus:border-primary/50"
                      autoFocus
                    />
                    {errors.email && (
                      <span className="text-destructive text-xs font-mono animate-in slide-in-from-left-1">
                        [ERROR] {errors.email.message}
                      </span>
                    )}
                  </div>

                  {/* Campo Password */}
                  <div className="space-y-2">
                    <div className="flex justify-between">
                      <label className="text-sm font-mono text-muted-foreground">
                        {'>'} password:
                      </label>
                      <span className="text-xs text-muted-foreground/50 font-mono">
                         max 20 chars
                      </span>
                    </div>
                    <div className="relative">
                      <Input
                        type={showPassword ? "text" : "password"}
                        {...register("password")}
                        placeholder="••••••••••••"
                        maxLength={20}
                        className="font-mono pr-10 bg-black/20 border-white/10 focus:border-primary/50"
                      />
                      <button
                        type="button"
                        onClick={() => setShowPassword(!showPassword)}
                        className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground transition-colors"
                      >
                        {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                      </button>
                    </div>
                    {errors.password && (
                      <span className="text-destructive text-xs font-mono animate-in slide-in-from-left-1">
                        [ERROR] {errors.password.message}
                      </span>
                    )}
                  </div>

                  <div className="flex items-center justify-between text-xs sm:text-sm pt-2">
                    <a href="#" className="text-primary hover:underline font-mono">
                      Esqueceu sua senha?
                    </a>
                  </div>

                  <Button
                    type="submit"
                    variant="default"
                    className="w-full group bg-primary hover:bg-primary/90 text-primary-foreground font-mono mt-4"
                    disabled={isSubmitting}
                  >
                    {isSubmitting ? (
                      <>
                        <Loader2 className="h-4 w-4 animate-spin mr-2" />
                        AUTHENTICATING...
                      </>
                    ) : (
                      <>
                        ACCESS TERMINAL
                        <ArrowRight className="h-4 w-4 ml-2 transition-transform group-hover:translate-x-1" />
                      </>
                    )}
                  </Button>
                </form>

                <div className="mt-6 text-center text-sm text-muted-foreground font-mono">
                  Novo no Pwnned?{" "}
                  <Link to="/signup" className="text-primary hover:underline">
                    Inicializar Usuário
                  </Link>
                </div>
              </div>
            )}
          </div>
        </div>

        <div className="text-center mt-6 animate-in fade-in duration-700">
          <Link to="/" className="text-xs font-mono text-muted-foreground hover:text-primary transition-colors">
            cd .. (Back to Home)
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Login;