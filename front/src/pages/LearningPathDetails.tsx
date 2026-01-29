import { useState, useEffect, useContext } from "react";
import { useParams, Link, Navigate } from "react-router-dom";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { 
  ArrowLeft, Terminal, 
  Play, ChevronRight, CheckCircle2, Loader2, AlertTriangle
} from "lucide-react";

import { api } from "@/lib/api"; 
import { AuthContext } from "@/context/AuthContext"; 
import jsPDF from "jspdf";
import { learningPathsData } from "@/data/learningpaths";
import { useToast } from "@/hooks/use-toast"; // Importando Toast para feedback

const LearningPathDetails = () => {
  const { id } = useParams();
  const pathId = id || ""; 
  const auth = useContext(AuthContext);
  const { toast } = useToast();

  const [activeModule, setActiveModule] = useState(0); 
  const [showCertModal, setShowCertModal] = useState(false);
  const [userNameForCert, setUserNameForCert] = useState("");
  const [isGenerating, setIsGenerating] = useState(false);
  const [alreadyHasCertificate, setAlreadyHasCertificate] = useState(false);
  
  // Novo estado para o loading do Lab
  const [isStartingLab, setIsStartingLab] = useState(false);

  const user = auth?.user;
  const userId = user?.userId ? String(user.userId) : null;

  useEffect(() => {
    const checkCertificateStatus = async () => {
      if (!userId || !pathId) return;
      try {
        const response = await api.get(`/certificates/exists`, {
          params: { userId: userId, learningPathId: pathId }
        });
        setAlreadyHasCertificate(response.data);
      } catch (err) {
        console.error("Erro ao verificar status do certificado:", err);
      }
    };
    checkCertificateStatus();
  }, [pathId, userId]);

  const path = learningPathsData.find((p) => String(p.id) === String(pathId));

  // Função Lógica Antiga: Iniciar Laboratório via API
  const handleStartLab = async () => {
    // @ts-ignore
    if (!path?.labId) {
      toast({ title: "Erro", description: "Laboratório não configurado.", variant: "destructive" });
      return;
    }

    setIsStartingLab(true);
    try {
      // @ts-ignore
      const response = await api.post(`/labs/${path.labId}/start`);
      
      const { url } = response.data;
      
      toast({
        title: "Laboratório Iniciado!",
        description: "Ambiente provisionado. Abrindo em nova aba...",
        className: "bg-green-500 border-none text-white"
      });

      // Abre a URL do laboratório em uma nova aba
      window.open(url, '_blank'); 

    } catch (error) {
      console.error("Erro ao iniciar lab:", error);
      toast({ 
        title: "Falha ao iniciar", 
        description: "Não foi possível instanciar o laboratório. Tente novamente.", 
        variant: "destructive" 
      });
    } finally {
      setIsStartingLab(false);
    }
  };

  const handleIssueCertificate = async () => {
    if (!userNameForCert.trim() || !userId) return;
    setIsGenerating(true);
    try {
      const certificatePayload = {
        title: `Certificado ${path?.title}`,
        url: `http://pwnned.tech/verify/${userId}-${pathId}`, 
        userId: userId, 
        learningPathId: String(pathId)
      };
      await api.post("/certificates", certificatePayload);
      if (path) generatePDF(userNameForCert, path.title, String(pathId));
      setAlreadyHasCertificate(true);
      setShowCertModal(false);
      toast({ title: "Sucesso!", description: "Certificado emitido." });
    } catch (error) {
      console.error("Erro na emissão:", error);
      toast({ title: "Erro", description: "Falha ao emitir certificado.", variant: "destructive" });
    } finally {
      setIsGenerating(false);
    }
  };

  const generatePDF = (name: string, pathTitle: string, pId: string) => {
    const doc = new jsPDF({ orientation: "landscape", unit: "mm", format: "a4" });
    const width = doc.internal.pageSize.getWidth();
    const height = doc.internal.pageSize.getHeight();
    doc.setFillColor(10, 10, 12); 
    doc.rect(0, 0, width, height, "F");
    doc.setDrawColor(6, 182, 212); 
    doc.setLineWidth(1.5);
    doc.rect(12, 12, width - 24, height - 24);
    doc.setTextColor(255, 255, 255);
    doc.setFontSize(40);
    doc.text("CERTIFICADO DE CONCLUSÃO", width / 2, 60, { align: "center" });
    doc.setFontSize(30);
    doc.text(name.toUpperCase(), width / 2, 110, { align: "center" });
    doc.setTextColor(6, 182, 212);
    doc.text(pathTitle, width / 2, 140, { align: "center" });
    doc.save(`Certificado-${pathTitle}.pdf`);
  };

  const renderContent = (module: any) => {
    return module.sections.map((section: any, sIdx: number) => (
      <div key={sIdx} className="mb-10 last:mb-0">
        <h2 className="text-2xl font-bold text-primary mb-6 flex items-center gap-3">
          <span className="h-8 w-1 bg-primary rounded-full" />
          {section.title}
        </h2>
        <div className="space-y-4">
          {section.content.split(/([\s\S]*?)/gm).map((part: string, pIdx: number) => {
            if (part.startsWith('')) {
              const lines = part.split('\n');
              const lang = lines[0].replace('', '').trim() || 'code';
              const code = lines.slice(1, -1).join('\n').trim();
              return (
                <div key={pIdx} className="my-6 relative">
                  <div className="absolute -top-2 left-4 px-2 py-0.5 text-[10px] font-mono bg-primary text-primary-foreground rounded uppercase">{lang}</div>
                  <pre className="p-5 rounded-xl bg-black/60 border border-white/10 font-mono text-sm text-cyan-400 overflow-x-auto shadow-2xl">
                    <code>{code}</code>
                  </pre>
                </div>
              );
            }
            return part.split('\n').map((line, lIdx) => (
              line.trim() && <p key={lIdx} className="text-muted-foreground leading-relaxed text-lg">{line.trim()}</p>
            ));
          })}
        </div>
      </div>
    ));
  };
  if (auth?.isLoading) {
    return (
      <div className="min-h-screen bg-background flex items-center justify-center">
        <Loader2 className="h-10 w-10 animate-spin text-primary" />
      </div>
    );
  }

  if (!auth?.isAuthenticated || !auth?.user) {
    return <Navigate to="/login" replace />;
  }

  if (!path) return <Navigate to="/dashboard" replace />;

  const modules = path.modulesContent || [];
  const currentModuleData = modules[activeModule] || { title: "Overview", content: "" };

  return (
    <div className="min-h-screen bg-background text-foreground selection:bg-primary/30">
      <Navbar simple={true} /> 
      <main className="container mx-auto px-4 pt-24 pb-12">
        <Link to="/dashboard" className="inline-flex items-center gap-2 text-muted-foreground hover:text-primary mb-6 transition-colors">
          <ArrowLeft className="h-4 w-4" /> Voltar para Dashboard
        </Link>

        <div className="mb-8">
          <Badge variant={path.difficulty.toLowerCase() as any} className="mb-4 px-3 py-1 text-sm">{path.difficulty}</Badge>
          <h1 className="text-4xl md:text-5xl font-bold tracking-tight text-white mb-2">{path.title}</h1>
          <p className="text-muted-foreground text-lg">{path.description}</p>
        </div>

        <div className="grid lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2">
            <Card variant="glass" className="min-h-[60vh] border-white/5 bg-zinc-900/40 backdrop-blur-md shadow-2xl">
              <CardHeader className="border-b border-white/5 bg-white/5">
                <CardTitle className="flex items-center gap-2 text-primary">
                  <Terminal className="h-5 w-5" /> {currentModuleData.title}
                </CardTitle>
              </CardHeader>
              <CardContent className="pt-8 pb-12 px-8">
                {renderContent(currentModuleData.content)}
              </CardContent>
            </Card>
            
            <div className="flex justify-between mt-8">
              <Button variant="outline" className="border-white/10 hover:bg-white/5" disabled={activeModule === 0} onClick={() => setActiveModule(prev => prev - 1)}>
                Anterior
              </Button>
              <Button 
                variant="glow" 
                className="px-8"
                onClick={() => activeModule === modules.length - 1 ? setShowCertModal(true) : setActiveModule(prev => prev + 1)}
                disabled={activeModule === modules.length - 1 && alreadyHasCertificate}
              >
                {activeModule === modules.length - 1 ? (alreadyHasCertificate ? "Concluído" : "Finalizar") : "Próximo"}
                <ChevronRight className="ml-2 h-4 w-4" />
              </Button>
            </div>
          </div>

          <div className="space-y-6">
            <Card variant="neon" className="bg-primary/5 border-primary/20 shadow-[0_0_30px_-10px_rgba(6,182,212,0.3)]">
              <CardContent className="p-6 text-center">
                <div className="h-16 w-16 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-4 animate-pulse">
                   <Play className="h-8 w-8 text-primary ml-1" />
                </div>
                <h3 className="text-xl font-bold mb-2 text-white">Laboratório Prático</h3>
                <p className="text-sm text-muted-foreground mb-6">Ambiente isolado Docker rodando no cluster.</p>
                
                <Button 
                  variant="glow" 
                  className="w-full py-6 text-lg font-bold transition-all hover:scale-105"
                  onClick={handleStartLab}
                  disabled={isStartingLab}
                >
                  {isStartingLab ? (
                    <>
                      <Loader2 className="mr-2 h-5 w-5 animate-spin" />
                      INICIANDO...
                    </>
                  ) : (
                    "INICIAR LAB"
                  )}
                </Button>
              </CardContent>
            </Card>
            
            <Card variant="glass" className="bg-zinc-900/30 border-white/5">
              <CardContent className="p-6">
                <h4 className="text-xs uppercase tracking-widest text-muted-foreground mb-6 font-bold flex items-center gap-2">
                  <span className="h-1 w-1 bg-primary rounded-full"></span> Progresso
                </h4>
                <div className="space-y-2">
                  {modules.map((m, i) => (
                    <button 
                      key={i} 
                      onClick={() => setActiveModule(i)} 
                      className={`w-full text-left p-3 rounded-md border transition-all flex items-center gap-3 text-sm ${
                        activeModule === i 
                        ? 'bg-primary/10 border-primary/40 text-primary font-medium shadow-[0_0_10px_-5px_rgba(6,182,212,0.5)]' 
                        : 'border-transparent text-muted-foreground hover:bg-white/5 hover:text-white'
                      }`}
                    >
                      <span className={`text-[10px] font-mono w-5 h-5 flex items-center justify-center rounded border ${activeModule === i ? 'border-primary bg-primary text-black' : 'border-white/10'}`}>
                        {i + 1}
                      </span>
                      {m.title}
                    </button>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>

      {/* Modal de Certificado (mesma lógica) */}
      {showCertModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/90 backdrop-blur-md animate-in fade-in duration-300">
          <Card className="w-full max-w-md border-primary/40 bg-zinc-950 shadow-2xl shadow-primary/10">
            <CardHeader className="text-center pt-10">
                <div className="h-20 w-20 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-4">
                  <CheckCircle2 className="h-10 w-10 text-primary" />
                </div>
                <CardTitle className="text-2xl text-white">Parabéns, Hacker!</CardTitle>
                <p className="text-muted-foreground mt-2">Você concluiu a trilha com sucesso.</p>
            </CardHeader>
            <CardContent className="space-y-4 pb-10 px-8">
              <div className="space-y-2">
                <label className="text-xs uppercase font-bold text-muted-foreground ml-1">Nome no Certificado</label>
                <input 
                  type="text" 
                  value={userNameForCert} 
                  onChange={(e) => setUserNameForCert(e.target.value)} 
                  className="w-full rounded-xl border border-white/10 bg-white/5 px-4 py-4 text-white focus:outline-none focus:ring-2 focus:ring-primary/50 transition-all placeholder:text-white/20" 
                  placeholder="Ex: John Doe" 
                />
              </div>
              <Button variant="glow" className="w-full py-7 text-lg" onClick={handleIssueCertificate} disabled={isGenerating || !userNameForCert.trim()}>
                {isGenerating ? <Loader2 className="animate-spin mr-2" /> : "EMITIR MEU CERTIFICADO"}
              </Button>
              <Button variant="ghost" className="w-full text-muted-foreground hover:text-white" onClick={() => setShowCertModal(false)}>Fechar</Button>
            </CardContent>
          </Card>
        </div>
      )}
      <Footer />
    </div>
  );
};

export default LearningPathDetails;