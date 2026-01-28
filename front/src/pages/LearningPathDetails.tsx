import { useState, useEffect } from "react";
import { useParams, Link, Navigate } from "react-router-dom";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Zap, Clock, BookOpen, Terminal, Play, ChevronRight, CheckCircle2 } from "lucide-react";

import { learningPathsData } from "@/data/learningPaths";

const LearningPathDetails = () => {
  const { id } = useParams();
  // Estado para controlar qual módulo está ativo (começa no 0)
  const [activeModule, setActiveModule] = useState(0); 

  const pathId = parseInt(id);
  const path = learningPathsData.find((p) => p.id === pathId);

  // Reseta o módulo para 0 se o usuário trocar de curso via URL
  useEffect(() => {
    setActiveModule(0);
  }, [pathId]);

  if (!path) {
    return <Navigate to="/dashboard" replace />;
  }

  // Pega o conteúdo do módulo atual (ou fallback seguro)
  const currentModuleData = path.modulesContent ? path.modulesContent[activeModule] : { title: "Overview", content: path.content || "" };

  const renderContent = (content) => {
    if (!content) return <p className="text-muted-foreground">Select a module to start learning.</p>;
    
    const sections = content.split(/(?=##\s)/);
    
    return sections.map((section, index) => {
      const lines = section.trim().split('\n');
      const elements = [];
      let inCodeBlock = false;
      let codeContent = '';
      let codeLanguage = '';

      lines.forEach((line, lineIndex) => {
        if (line.startsWith('```')) {
          if (inCodeBlock) {
            elements.push(
              <div key={`code-${index}-${lineIndex}`} className="relative my-4 rounded-md overflow-hidden border border-white/10 bg-black/50">
                 <div className="px-4 py-1 bg-white/5 text-xs text-muted-foreground border-b border-white/5 uppercase font-mono">
                    {codeLanguage}
                 </div>
                 <pre className="p-4 overflow-x-auto text-sm font-mono text-emerald-400">
                   <code>{codeContent.trim()}</code>
                 </pre>
              </div>
            );
            codeContent = '';
            inCodeBlock = false;
          } else {
            codeLanguage = line.slice(3) || 'text';
            inCodeBlock = true;
          }
        } else if (inCodeBlock) {
          codeContent += line + '\n';
        } else if (line.startsWith('## ')) {
          elements.push(
            <h2 key={`h2-${index}-${lineIndex}`} className="text-2xl font-bold mt-8 mb-4 text-primary border-b border-white/10 pb-2">
              {line.slice(3)}
            </h2>
          );
        } else if (line.startsWith('### ')) {
          elements.push(
            <h3 key={`h3-${index}-${lineIndex}`} className="text-xl font-semibold mt-6 mb-3 text-foreground/90">
              {line.slice(4)}
            </h3>
          );
        } else if (line.trim()) {
          elements.push(
            <p key={`p-${index}-${lineIndex}`} className="text-muted-foreground mb-4 leading-relaxed">
              {line}
            </p>
          );
        }
      });

      return <div key={index}>{elements}</div>;
    });
  };

  return (
    <div className="min-h-screen bg-background">
      <Navbar simple={true} /> 
      
      <main className="container mx-auto px-4 pt-24 pb-12">
        <Link to="/dashboard" className="inline-flex items-center gap-2 text-muted-foreground hover:text-primary transition-colors mb-6 group">
          <ArrowLeft className="h-4 w-4 group-hover:-translate-x-1 transition-transform" />
          Voltar para Dashboard
        </Link>

        <div className="mb-8 animate-in fade-in slide-in-from-bottom-4 duration-700">
          <div className="flex flex-wrap items-center gap-3 mb-4">
            <Badge variant={path.difficulty.toLowerCase()} className="capitalize">
              {path.difficulty}
            </Badge>
            <Badge variant="glow" className="flex items-center gap-1">
              <Zap className="h-3 w-3" />
              {path.xp} XP
            </Badge>
            <Badge variant="outline" className="flex items-center gap-1">
              <Clock className="h-3 w-3" />
              {path.duration}
            </Badge>
            <Badge variant="outline" className="flex items-center gap-1">
              <BookOpen className="h-3 w-3" />
              {path.modulesContent?.length || 0} Módulos
            </Badge>
          </div>
          
          <h1 className="text-4xl md:text-5xl font-bold mb-4 text-gradient-to-r from-white to-white/60">{path.title}</h1>
          <p className="text-lg text-muted-foreground max-w-3xl leading-relaxed">{path.description}</p>
        </div>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Main Content Area */}
          <div className="lg:col-span-2 animate-in fade-in slide-in-from-bottom-8 duration-700 delay-100">
            <Card variant="glass" className="border-white/5 min-h-[60vh]">
              <CardHeader className="border-b border-white/5 pb-4">
                <CardTitle className="flex items-center gap-2 text-xl">
                  <Terminal className="h-5 w-5 text-primary" />
                  {/* Título dinâmico do módulo */}
                  {currentModuleData.title}
                </CardTitle>
              </CardHeader>
              <CardContent className="prose prose-invert max-w-none pt-6">
                {/* Conteúdo dinâmico do módulo */}
                {renderContent(currentModuleData.content)}
              </CardContent>
            </Card>
            
            {/* Navegação entre módulos no rodapé do conteúdo */}
            <div className="flex justify-between mt-6">
                <Button 
                    variant="outline" 
                    disabled={activeModule === 0}
                    onClick={() => {
                        setActiveModule(prev => prev - 1);
                        window.scrollTo({ top: 0, behavior: 'smooth' });
                    }}
                >
                    Módulo Anterior
                </Button>
                <Button 
                    variant="glow"
                    disabled={!path.modulesContent || activeModule === path.modulesContent.length - 1}
                    onClick={() => {
                        setActiveModule(prev => prev + 1);
                        window.scrollTo({ top: 0, behavior: 'smooth' });
                    }}
                >
                    Próximo módulo <ChevronRight className="ml-2 h-4 w-4" />
                </Button>
            </div>
          </div>

          {/* Sidebar Modules List */}
          <div className="space-y-6 animate-in fade-in slide-in-from-right-8 duration-700 delay-200">
            <Card variant="neon" className="border-primary/20">
              <CardContent className="p-6 text-center">
                <div className="p-4 rounded-full bg-primary/10 w-fit mx-auto mb-4 animate-pulse-slow">
                  <Play className="h-8 w-8 text-primary fill-primary/20" />
                </div>
                <h3 className="text-xl font-bold mb-2">Pronto para praticar?</h3>
                <p className="text-muted-foreground text-sm mb-6">
                  Aplique o que você aprendeu em nosso ambiente prático de laboratório.
                </p>
                <Link to={path.labUrl || "#"}>
                    <Button variant="glow" size="lg" className="w-full font-bold">
                      INICIAR LABORATÓRIO
                    <ChevronRight className="h-4 w-4 ml-2" />
                    </Button>
                </Link>
              </CardContent>
            </Card>

            <Card variant="glass">
              <CardContent className="p-6">
                <h4 className="font-semibold mb-4 text-sm uppercase tracking-wider text-muted-foreground">Módulos do curso</h4>
                <div className="space-y-3">
                  {/* Renderização dinâmica da lista de módulos */}
                  {path.modulesContent ? path.modulesContent.map((module, index) => {
                    const isActive = activeModule === index;
                    return (
                        <div
                        key={index}
                        onClick={() => setActiveModule(index)}
                        className={`flex items-center gap-3 p-3 rounded-lg border transition-all cursor-pointer group ${
                            isActive 
                            ? 'bg-primary/10 border-primary/30 shadow-[0_0_15px_rgba(6,182,212,0.1)]' 
                            : 'bg-black/20 border-transparent hover:bg-white/5 hover:border-white/10'
                        }`}
                        >
                        <div
                            className={`w-6 h-6 rounded-full flex items-center justify-center text-xs font-mono border transition-colors ${
                            isActive
                                ? 'bg-primary text-black border-primary' 
                                : 'bg-transparent text-muted-foreground border-white/20 group-hover:border-white/40'
                            }`}
                        >
                            {index + 1}
                        </div>
                        <div className="flex-1 min-w-0">
                            <span className={`text-sm block truncate ${isActive ? 'text-primary font-medium' : 'text-muted-foreground group-hover:text-foreground'}`}>
                                {module.title}
                            </span>
                        </div>
                        {isActive && <Zap className="h-3 w-3 text-primary animate-pulse" />}
                        </div>
                    );
                  }) : (
                      <p className="text-sm text-muted-foreground">Nenhum módulo disponível.</p>
                  )}
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
      
      <Footer />
    </div>
  );
};

export default LearningPathDetails;