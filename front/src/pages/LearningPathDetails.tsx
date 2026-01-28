import { useState, useEffect, useContext } from "react";
import { useParams, Link, Navigate } from "react-router-dom";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { 
  ArrowLeft, Terminal, 
  Play, ChevronRight, CheckCircle2, Loader2 
} from "lucide-react";

import { api } from "@/lib/api"; 
import { AuthContext } from "@/context/AuthContext"; 
import jsPDF from "jspdf";
import { learningPathsData } from "@/data/learningPaths";

const LearningPathDetails = () => {
  const { id } = useParams();
  const pathId = id || ""; 
  const auth = useContext(AuthContext);

  const [activeModule, setActiveModule] = useState(0); 
  const [showCertModal, setShowCertModal] = useState(false);
  const [userNameForCert, setUserNameForCert] = useState("");
  const [isGenerating, setIsGenerating] = useState(false);
  const [alreadyHasCertificate, setAlreadyHasCertificate] = useState(false);

  //userId declarado antes do uso no useEffect
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

  if (auth?.isLoading) {
    return (
      <div className="min-h-screen bg-background flex items-center justify-center">
        <Loader2 className="h-10 w-10 animate-spin text-primary" />
        <span className="ml-3 text-muted-foreground">Validando sessão...</span>
      </div>
    );
  }

  if (!auth?.isAuthenticated || !auth?.user) {
    return <Navigate to="/login" replace />;
  }

  const path = learningPathsData.find((p) => String(p.id) === String(pathId));
  if (!path) return <Navigate to="/dashboard" replace />;

  const modules = path.modulesContent || [];
  const currentModuleData = modules[activeModule] || { title: "Overview", content: "" };

  const handleIssueCertificate = async () => {
    if (!userNameForCert.trim() || !userId) return;
    setIsGenerating(true);
    try {
      const certificatePayload = {
        title: `Certificado ${path.title}`,
        url: `http://localhost:5173/verify/${userId}-${pathId}`, 
        userId: userId, 
        learningPathId: String(pathId)
      };
      await api.post("/certificates", certificatePayload);
      generatePDF(userNameForCert, path.title, String(pathId));
      setAlreadyHasCertificate(true);
      setShowCertModal(false);
    } catch (error) {
      console.error("Erro na emissão:", error);
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

  const renderContent = (content: string) => {
    if (!content) return null;
    // Regex melhorada para capturar blocos de código sem perder o conteúdo interno
    const parts = content.split(/(```[\s\S]*?```)/gm);

    return parts.map((part, idx) => {
      if (part.startsWith('```')) {
        const lines = part.split('\n');
        const language = lines[0].replace('```', '').trim() || 'code';
        const code = lines.slice(1, -1).join('\n').trim();
        
        return (
          <div key={idx} className="my-6 relative">
            <div className="absolute -top-2 left-4 px-2 py-0.5 text-[10px] font-mono uppercase bg-primary text-primary-foreground rounded z-10">
              {language}
            </div>
            <pre className="p-4 rounded-lg bg-black/80 border border-white/10 font-mono text-sm text-cyan-400 overflow-x-auto">
              <code>{code}</code>
            </pre>
          </div>
        );
      }

      const lines = part.split('\n');
      return (
        <div key={idx} className="space-y-2">
          {lines.map((line, lIdx) => {
            const trimmedLine = line.trim();
            if (trimmedLine.startsWith('## ')) return <h2 key={lIdx} className="text-2xl font-bold text-primary mt-6 mb-4">{trimmedLine.slice(3)}</h2>;
            if (trimmedLine.startsWith('### ')) return <h3 key={lIdx} className="text-xl font-semibold text-white mt-4 mb-2">{trimmedLine.slice(4)}</h3>;
            if (trimmedLine === '') return null;
            return <p key={lIdx} className="text-muted-foreground leading-relaxed">{trimmedLine}</p>;
          })}
        </div>
      );
    });
  };

  return (
    <div className="min-h-screen bg-background text-foreground">
      <Navbar simple={true} /> 
      <main className="container mx-auto px-4 pt-24 pb-12">
        <Link to="/dashboard" className="inline-flex items-center gap-2 text-muted-foreground hover:text-primary mb-6 transition-colors">
          <ArrowLeft className="h-4 w-4" /> Voltar para Dashboard
        </Link>

        <div className="mb-8">
          <Badge variant={path.difficulty.toLowerCase() as any} className="mb-4">{path.difficulty}</Badge>
          <h1 className="text-4xl font-bold">{path.title}</h1>
        </div>

        <div className="grid lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2">
            <Card variant="glass" className="min-h-[60vh] border-white/5 bg-zinc-900/50 backdrop-blur-sm">
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
              <Button variant="outline" disabled={activeModule === 0} onClick={() => setActiveModule(prev => prev - 1)}>
                Anterior
              </Button>
              <Button 
                variant="glow" 
                onClick={() => activeModule === modules.length - 1 ? setShowCertModal(true) : setActiveModule(prev => prev + 1)}
                disabled={activeModule === modules.length - 1 && alreadyHasCertificate}
              >
                {activeModule === modules.length - 1 ? (alreadyHasCertificate ? "Concluído" : "Finalizar") : "Próximo"}
                <ChevronRight className="ml-2 h-4 w-4" />
              </Button>
            </div>
          </div>

          <div className="space-y-6">
            <Card variant="neon" className="bg-primary/5 border-primary/20">
              <CardContent className="p-6 text-center">
                <Play className="h-10 w-10 text-primary mx-auto mb-4" />
                <h3 className="text-xl font-bold mb-4">Laboratório Prático</h3>
                <Link to={path.labUrl || "#"}>
                  <Button variant="glow" className="w-full py-6 text-lg font-bold">INICIAR LAB</Button>
                </Link>
              </CardContent>
            </Card>
            
            <Card variant="glass" className="bg-zinc-900/50">
              <CardContent className="p-6">
                <h4 className="text-xs uppercase tracking-widest text-muted-foreground mb-6 font-bold">Progresso do Curso</h4>
                <div className="space-y-3">
                  {modules.map((m, i) => (
                    <button 
                      key={i} 
                      onClick={() => setActiveModule(i)} 
                      className={`w-full text-left p-4 rounded-lg border transition-all flex items-center gap-3 ${
                        activeModule === i 
                        ? 'bg-primary/20 border-primary/50 text-primary font-medium' 
                        : 'border-white/5 text-muted-foreground hover:bg-white/5'
                      }`}
                    >
                      <span className="text-xs opacity-50 font-mono">{String(i + 1).padStart(2, '0')}</span>
                      {m.title}
                    </button>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>

      {showCertModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/90 backdrop-blur-md">
          <Card className="w-full max-w-md border-primary/40 bg-zinc-950 shadow-2xl shadow-primary/10">
            <CardHeader className="text-center pt-10">
                <div className="h-20 w-20 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-4">
                  <CheckCircle2 className="h-10 w-10 text-primary" />
                </div>
                <CardTitle className="text-2xl">Parabéns, Hacker!</CardTitle>
                <p className="text-muted-foreground mt-2">Você concluiu a trilha com sucesso.</p>
            </CardHeader>
            <CardContent className="space-y-4 pb-10 px-8">
              <div className="space-y-2">
                <label className="text-xs uppercase font-bold text-muted-foreground ml-1">Nome no Certificado</label>
                <input 
                  type="text" 
                  value={userNameForCert} 
                  onChange={(e) => setUserNameForCert(e.target.value)} 
                  className="w-full rounded-xl border border-white/10 bg-white/5 px-4 py-4 text-white focus:outline-none focus:ring-2 focus:ring-primary/50 transition-all" 
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