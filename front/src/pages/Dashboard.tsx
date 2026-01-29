import { useState, useEffect } from "react";
import DashboardSidebar from "@/components/DashboardSidebar";
import DashboardHeader from "@/components/DashboardHeader";
import CourseCard from "@/components/CourseCard"; 
import SkeletonCard from "@/components/SkeletonCard"; 
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent } from "@/components/ui/card";
import { Search, Filter, Trophy, Target, Flame, Clock } from "lucide-react";
import { learningPathsData } from "@/data/learningpaths";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext"

const mockLearningPaths = [
  {
    id: 1,
    title: "SQL Injection Mastery",
    description: "Aprenda a identificar e explorar vulnerabilidades de injeção de SQL em aplicações web.",
    difficulty: "Medium",
    xp: 500,
    duration: "4h",
    students: 12500,
    progress: 45,
  },
  {
    id: 2,
    title: "Cross-Site Scripting (XSS)",
    description: "Domine a arte dos ataques XSS. Compreenda reflected, stored, e DOM-based XSS.",
    difficulty: "Easy",
    xp: 750,
    duration: "6h",
    students: 8900,
    progress: 20,
  },
  {
    id: 3,
    title: "Buffer Overflow Exploitation",
    description: "Análise detalhada das vulnerabilidades de corrupção de memória. Estouro de pilha e de heap.",
    difficulty: "Hard",
    xp: 1200,
    duration: "12h",
    students: 4500,
  },
  {
    id: 4,
    title: "Active Directory Attacks",
    description: "Comprometer redes corporativas. Kerberoasting, Pass-the-Hash e Golden Tickets.",
    difficulty: "Hard",
    xp: 2000,
    duration: "20h",
    students: 2100,
  },
  {
    id: 5,
    title: "Web App Reconnaissance",
    description: "Técnicas de coleta e enumeração de informações. Descoberta e identificação de subdomínios.",
    difficulty: "Easy",
    xp: 400,
    duration: "3h",
    students: 15000,
    progress: 100,
  },
  {
    id: 6,
    title: "Privilege Escalation Linux",
    description: "Escalar privilégios de usuário para root em sistemas Linux. Exploração de SUID, capacidades e vulnerabilidades do kernel.",
    difficulty: "Medium",
    xp: 800,
    duration: "8h",
    students: 7200,
  },
];

const Dashboard = () => {
  const auth = useContext(AuthContext);
  const user = auth?.user;
  const isAuthLoding = auth?.isLoading;

  const [isLoading, setIsLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedDifficulty, setSelectedDifficulty] = useState(null);

  useEffect(() => {
    const timer = setTimeout(() => setIsLoading(false), 1500);
    return () => clearTimeout(timer);
  }, []);

  const filteredPaths = learningPathsData.filter((path) => {
    const matchesSearch = path.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
                          path.description.toLowerCase().includes(searchQuery.toLowerCase());
    
    const matchesDifficulty = !selectedDifficulty || 
      path.difficulty.toLowerCase() === selectedDifficulty.toLowerCase();
      
    return matchesSearch && matchesDifficulty;
  });

  const stats = {
    totalXP: 2750,
    completedPaths: 1,
    currentStreak: 7,
    hoursLearned: 23,
  };

  return (
    <div className="min-h-screen bg-background">
      <DashboardSidebar />
      
      <main className="ml-16 md:ml-64 p-6 md:p-8 transition-all duration-300">
        
        <DashboardHeader 
          title={isAuthLoding ? "Carregando..." : `Welcome back, ${user?.username || "Hacker"}`} 
          subtitle="Continue your journey to becoming a security expert"
        />

        {/* Grid de Estatísticas (Stats) - TOTALMENTE REMOVIDO */}
        {/* <div className="grid grid-cols-2 lg:grid-cols-2 gap-4 mb-8">
          <Card variant="glass" className="border-none bg-primary/5">
            <CardContent className="p-4 flex items-center gap-3">
              <div className="p-2 rounded-lg bg-primary/10">
                <Trophy className="h-5 w-5 text-primary" />
              </div>
              <div>
                <p className="text-2xl font-bold text-foreground">{stats.totalXP.toLocaleString()}</p>
                <p className="text-xs text-muted-foreground">Total XP</p>
              </div>
            </CardContent>
          </Card>
          
          <Card variant="glass" className="border-none bg-primary/5">
            <CardContent className="p-4 flex items-center gap-3">
              <div className="p-2 rounded-lg bg-emerald-500/10">
                <Target className="h-5 w-5 text-emerald-400" />
              </div>
              <div>
                <p className="text-2xl font-bold text-foreground">{stats.completedPaths}</p>
                <p className="text-xs text-muted-foreground">Paths Completed</p>
              </div>
            </CardContent>
          </Card> 
        </div> 
        */}

        <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-6">
            <h2 className="text-xl font-semibold text-foreground">Available Learning Paths</h2>
            
            {/* REMOVIDO: Barra de Pesquisa */}
            {/* <div className="flex gap-4 w-full md:w-auto">
              <div className="relative w-full md:w-64">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <Input
                  placeholder="Search paths..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className="pl-10 w-full bg-background/50 border-white/10 focus:border-primary/50"
                />
              </div>
            </div>
            */}
        </div>

        <div className="flex gap-2 mb-8 flex-wrap">
          <Badge
            variant={!selectedDifficulty ? "default" : "outline"}
            className="cursor-pointer hover:bg-primary/80 transition-colors"
            onClick={() => setSelectedDifficulty(null)}
          >
            All
          </Badge>
          {["Easy", "Medium", "Hard"].map((diff) => (
            <Badge
              key={diff}
              variant={selectedDifficulty === diff ? "default" : "outline"}
              className="cursor-pointer capitalize hover:bg-primary/80 transition-colors"
              onClick={() => setSelectedDifficulty(selectedDifficulty === diff ? null : diff)}
            >
              {diff}
            </Badge>
          ))}
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {isLoading ? (
            Array.from({ length: 3 }).map((_, i) => <SkeletonCard key={i} />)
          ) : (
            filteredPaths.map((path, index) => (
              <div
                key={path.id}
                className="animate-in fade-in slide-in-from-bottom-4 duration-500"
                style={{ animationDelay: `${index * 0.1}s` }}
              >
                <CourseCard {...path} />
              </div>
            ))
          )}
        </div>

        {!isLoading && filteredPaths.length === 0 && (
          <div className="text-center py-12">
            <p className="text-muted-foreground">Não foram encontrados percursos de aprendizagem.</p>
          </div>
        )}
      </main>
    </div>
  );
};

export default Dashboard;