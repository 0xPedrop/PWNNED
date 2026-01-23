import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod"; 
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Terminal, Eye, EyeOff, ArrowRight, Loader2, Check } from "lucide-react";
import MatrixBackground from "@/components/MatrixBackground";
import { api } from "@/lib/api"; 
import { useToast } from "@/hooks/use-toast"; 

const signupSchema = z.object({
  username: z
    .string()
    .min(1, "Username é obrigatório")
    .min(4, "O nome de usuário deve ter entre 4 e 10 caracteres.") 
    .max(20, "O nome de usuário deve ter entre 4 e 20 caracteres."), 
  email: z
    .string()
    .min(1, "Email é obrigatório")
    .email("Formato inválido para e-mail"), 
  password: z
    .string()
    .min(1, "A senha é obrigatória")
    .min(8, "A senha deve ter entre 8 e 20 caracteres.") 
    .max(20, "A senha deve ter entre 8 e 20 caracteres."), 
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: "As senhas não coincidem",
  path: ["confirmPassword"],
});

type SignupFormValues = z.infer<typeof signupSchema>;

const Signup = () => {
  const navigate = useNavigate();
  const { toast } = useToast(); 
  const [isExpanded, setIsExpanded] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<SignupFormValues>({
    resolver: zodResolver(signupSchema),
    mode: "onChange", 
  });

  const passwordValue = watch("password", "");
  const confirmPasswordValue = watch("confirmPassword", "");

  const passwordStrength = () => {
    const password = passwordValue;
    let strength = 0;
    if (password.length >= 8) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[^A-Za-z0-9]/.test(password)) strength++;
    return strength;
  };

  const onSubmit = async (data: SignupFormValues) => {
    try {
      // Prepara o payload (remove confirmPassword pois o Java não espera isso)
      const { confirmPassword, ...payload } = data;

      // Chamada Real para a API
      await api.post("/users", payload);
      
      // Sucesso
      toast({
        title: "Protocolo de Iniciação Concluído",
        description: "Usuário registrado no mainframe. Execute o login.",
        className: "bg-green-900 border-green-500 text-green-100 font-mono",
      });

      // Redireciona
      navigate("/login");

    } catch (error: any) {
      console.error("Erro ao criar usuário:", error);
      
      // Tratamento de erro vindo do Backend
      const serverMessage = error.response?.data?.message || "Falha na conexão com o servidor.";
      
      toast({
        variant: "destructive",
        title: "System Error",
        description: `[FALHA] ${serverMessage}`,
        className: "font-mono",
      });
    }
  };

  const strengthLabels = ["Weak", "Fair", "Good", "Strong"];
  const strengthColors = ["bg-destructive", "bg-yellow-500", "bg-secondary", "bg-primary"];

  return (
    <div className="min-h-screen bg-background flex items-center justify-center p-4">
      <MatrixBackground />
      
      <div className={`relative z-10 w-full max-w-md transition-all duration-500 ease-in-out ${
        isExpanded ? 'scale-100' : 'scale-95'
      }`}>
        <div className="glass-card overflow-hidden shadow-2xl border border-white/10 rounded-lg backdrop-blur-md bg-black/40">
          
          {/* Header */}
          <div className="flex items-center gap-2 px-4 py-3 bg-muted/50 border-b border-white/5">
            <div className="flex gap-2">
              <div className="w-3 h-3 rounded-full bg-destructive/70" />
              <div className="w-3 h-3 rounded-full bg-yellow-500/70" />
              <div className="w-3 h-3 rounded-full bg-green-500/70" />
            </div>
            <span className="text-sm text-muted-foreground font-mono ml-2">
              {isExpanded ? 'pwnned@register:~' : 'pwnned@root:~'}
            </span>
          </div>

          <div className="p-6 transition-all duration-500">
            {!isExpanded ? (
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
                      ./create_user.sh
                    </span>
                    <span className="inline-block w-2 h-4 bg-primary animate-pulse ml-1" />
                  </div>
                  <p className="text-xs text-muted-foreground mt-3 text-center opacity-70">
                    Click terminal line to initialize user creation protocol...
                  </p>
                </button>
              </div>
            ) : (
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
                  <span className="text-foreground">./create_user.sh </span>
                </div>

                {/* Início do Formulário */}
                <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                  
                  {/* Username */}
                  <div className="space-y-2">
                    <label className="text-sm font-mono text-muted-foreground">
                      {'>'} username:
                    </label>
                    <Input
                      type="text"
                      {...register("username")} 
                      placeholder="hackerm4n"
                      maxLength={20}
                      className="font-mono bg-black/20 border-white/10 focus:border-primary/50"
                      autoFocus
                    />
                    {errors.username && (
                      <span className="text-destructive text-xs font-mono animate-in slide-in-from-left-1">
                        [ERROR] {errors.username.message}
                      </span>
                    )}
                  </div>

                  {/* Email */}
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

                  {/* Password */}
                  <div className="space-y-2">
                    <label className="text-sm font-mono text-muted-foreground">
                      {'>'} password:
                    </label>
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
                      <span className="text-destructive text-xs font-mono animate-in slide-in-from-left-1 block mt-1">
                        [ERROR] {errors.password.message}
                      </span>
                    )}

                    {/* Password Strength Meter */}
                    {passwordValue && !errors.password && (
                      <div className="space-y-2 animate-fade-in pt-1">
                        <div className="flex gap-1">
                          {[0, 1, 2, 3].map((i) => (
                            <div
                              key={i}
                              className={`h-1 flex-1 rounded-full transition-colors ${
                                i < passwordStrength() ? strengthColors[passwordStrength() - 1] : 'bg-muted/20'
                              }`}
                            />
                          ))}
                        </div>
                        <p className="text-xs text-muted-foreground text-right font-mono">
                          [{strengthLabels[passwordStrength() - 1] || "INSECURE"}]
                        </p>
                      </div>
                    )}
                  </div>

                  {/* Confirm Password */}
                  <div className="space-y-2">
                    <label className="text-sm font-mono text-muted-foreground">
                      {'>'} confirm_password:
                    </label>
                    <div className="relative">
                      <Input
                        type={showPassword ? "text" : "password"}
                        {...register("confirmPassword")}
                        placeholder="••••••••••••"
                        maxLength={20}
                        className="font-mono bg-black/20 border-white/10 focus:border-primary/50"
                      />
                      {confirmPasswordValue && passwordValue === confirmPasswordValue && (
                        <Check className="absolute right-3 top-1/2 -translate-y-1/2 h-4 w-4 text-green-500 animate-in zoom-in" />
                      )}
                    </div>
                    {errors.confirmPassword && (
                      <span className="text-destructive text-xs font-mono animate-in slide-in-from-left-1">
                        [ERROR] {errors.confirmPassword.message}
                      </span>
                    )}
                  </div>

                  <div className="flex items-start gap-2 text-sm pt-2">
                    <input type="checkbox" required className="mt-1 rounded bg-black/20 border-white/20 text-primary focus:ring-primary" />
                    <span className="text-muted-foreground text-xs">
                      I agree to the{" "}
                      <a href="#" className="text-primary hover:underline font-mono">Terms of Service</a>
                      {" "}and{" "}
                      <a href="#" className="text-primary hover:underline font-mono">Privacy Policy</a>
                    </span>
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
                        EXECUTING...
                      </>
                    ) : (
                      <>
                        INITIALIZE PROFILE
                        <ArrowRight className="h-4 w-4 ml-2 transition-transform group-hover:translate-x-1" />
                      </>
                    )}
                  </Button>
                </form>

                <div className="mt-6 text-center text-sm text-muted-foreground font-mono">
                  Já tem uma conta?{" "}
                  <Link to="/login" className="text-primary hover:underline">
                    ssh login
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

export default Signup;