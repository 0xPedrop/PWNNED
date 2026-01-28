import { useState, useEffect, useContext } from "react";
import { useParams, Link, Navigate } from "react-router-dom";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { 
  ArrowLeft, Zap, Terminal, 
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

  const user = auth.user;
  const userId = user?.userId ? String(user.userId) : null;
  const path = learningPathsData.find((p) => String(p.id) === String(pathId));
  
  if (!path) return <Navigate to="/dashboard" replace />;

  const modules = path.modulesContent || [];
  const currentModuleData = modules[activeModule] || { title: "Overview", content: "" };

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

  const handleIssueCertificate = async () => {
    if (!userNameForCert.trim() || !userId) {
      alert("Erro: ID do usuário não identificado.");
      return;
    }
    
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
      
    } catch (error: any) {
      console.error("Erro na emissão:", error.response?.data || error);
      alert(error.response?.data?.message || "Erro ao emitir certificado no servidor.");
    } finally {
      setIsGenerating(false);
    }
  };

  /**
   * GERADOR DE PDF 
   */
  const generatePDF = (name: string, pathTitle: string, pId: string) => {
    const doc = new jsPDF({ orientation: "landscape", unit: "mm", format: "a4" });
    const width = doc.internal.pageSize.getWidth();
    const height = doc.internal.pageSize.getHeight();

    // 1. Fundo Dark Profundo
    doc.setFillColor(10, 10, 12); 
    doc.rect(0, 0, width, height, "F");

    // 2. Bordas de Segurança (Moldura Hacker)
    doc.setDrawColor(6, 182, 212); // Cyan Primary
    doc.setLineWidth(0.5);
    doc.rect(5, 5, width - 10, height - 10); 
    doc.setLineWidth(1.5);
    doc.rect(12, 12, width - 24, height - 24);

    // 3. Header - Logo Estilizada
    doc.setTextColor(6, 182, 212);
    doc.setFont("helvetica", "bold");
    doc.setFontSize(45);
    doc.text("PWN", (width / 2) - 5, 45, { align: "right" });
    doc.setTextColor(255, 255, 255);
    doc.text("NED", (width / 2) - 5, 45, { align: "left" });

    // 4. Subtítulo Decorativo
    doc.setFontSize(10);
    doc.setFont("courier", "normal");
    doc.setTextColor(100, 100, 100);
    doc.text("OFFICIAL OFFENSIVE SECURITY CERTIFICATION", width / 2, 55, { align: "center", charSpace: 2 });

    // 5. Corpo do Certificado
    doc.setFont("helvetica", "normal");
    doc.setFontSize(16);
    doc.setTextColor(180, 180, 180);
    doc.text("Certificamos que o operador(a) de sistemas", width / 2, 85, { align: "center" });

    // 6. Nome do Aluno (Grande Destaque)
    doc.setFontSize(38);
    doc.setTextColor(255, 255, 255);
    doc.setFont("helvetica", "bold");
    doc.text(name.toUpperCase(), width / 2, 105, { align: "center" });

    // 7. Texto de Conclusão
    doc.setFontSize(16);
    doc.setFont("helvetica", "normal");
    doc.setTextColor(180, 180, 180);
    doc.text("completou com maestria e ética a trilha avançada:", width / 2, 125, { align: "center" });

    // 8. Título da Trilha (Destaque Cyan)
    doc.setFontSize(26);
    doc.setTextColor(6, 182, 212);
    doc.setFont("helvetica", "bold");
    doc.text(pathTitle.toUpperCase(), width / 2, 145, { align: "center" });

    // 9. Elemento de Design Central (Linha)
    doc.setDrawColor(30, 30, 35);
    doc.line(width / 4, 155, (width / 4) * 3, 155);

    // 10. Rodapé - Metadados de Autenticidade
    const dataEmissao = new Date().toLocaleDateString('pt-BR');
    doc.setFont("courier", "normal");
    doc.setFontSize(8);
    doc.setTextColor(80, 80, 80);
    
    doc.text(`DATA_EMISSAO: ${dataEmissao}`, 20, height - 20);
    doc.text(`PATH_ID: ${pId}`, 20, height - 16);
    doc.text(`STATUS: VERIFIED_BY_PWNNED_SYSTEM`, width - 20, height - 20, { align: "right" });
    doc.text(`HASH: ${Math.random().toString(16).slice(2, 15).toUpperCase()}`, width - 20, height - 16, { align: "right" });

    // 11. Selo Digital Simulado (Canto inferior direito)
    doc.setDrawColor(6, 182, 212);
    doc.rect(width - 45, height - 45, 25, 25);
    doc.setFontSize(6);
    doc.text("ENCRYPTED", width - 32.5, height - 32, { align: "center" });
    doc.text("ORIGINAL", width - 32.5, height - 35, { align: "center" });

    doc.save(`Certificado-Pwnned-${pathTitle}.pdf`);
  };

  const renderContent = (content: string) => {
    if (!content) return null;
    return content.split(/(?=##\s)/).map((section, idx) => {
      const lines = section.trim().split('\n');
      return (
        <div key={idx} className="mb-6">
          {lines.map((line, lIdx) => {
            if (line.startsWith('## ')) return <h2 key={lIdx} className="text-2xl font-bold text-primary mt-6 mb-4">{line.slice(3)}</h2>;
            if (line.startsWith('### ')) return <h3 key={lIdx} className="text-xl font-semibold text-white mt-4 mb-2">{line.slice(4)}</h3>;
            return <p key={lIdx} className="text-muted-foreground leading-relaxed mb-2">{line}</p>;
          })}
        </div>
      );
    });
  };

  return (
    <div className="min-h-screen bg-background">
      <Navbar simple={true} /> 
      <main className="container mx-auto px-4 pt-24 pb-12">
        <Link to="/dashboard" className="inline-flex items-center gap-2 text-muted-foreground hover:text-primary mb-6">
          <ArrowLeft className="h-4 w-4" /> Voltar para Dashboard
        </Link>

        <div className="mb-8">
          <div className="flex wrap items-center gap-3 mb-4">
            <Badge variant={path.difficulty.toLowerCase() as any}>{path.difficulty}</Badge>
           {/* <Badge variant="glow"><Zap className="h-3 w-3" /> {path.xp} XP</Badge> */}
          </div>
          <h1 className="text-4xl font-bold mb-4">{path.title}</h1>
        </div>

        <div className="grid lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2">
            <Card variant="glass" className="min-h-[60vh]">
              <CardHeader className="border-b border-white/5">
                <CardTitle className="flex items-center gap-2">
                  <Terminal className="h-5 w-5 text-primary" /> {currentModuleData.title}
                </CardTitle>
              </CardHeader>
              <CardContent className="pt-6">{renderContent(currentModuleData.content)}</CardContent>
            </Card>
            
            <div className="flex justify-between mt-6">
              <Button variant="outline" disabled={activeModule === 0} onClick={() => setActiveModule(prev => prev - 1)}>
                Anterior
              </Button>
              <Button 
                variant="glow" 
                onClick={() => activeModule === modules.length - 1 ? setShowCertModal(true) : setActiveModule(prev => prev + 1)}
                disabled={activeModule === modules.length - 1 && alreadyHasCertificate}
              >
                {activeModule === modules.length - 1 ? (alreadyHasCertificate ? "Certificado Emitido" : "Finalizar") : "Próximo"}
                <ChevronRight className="ml-2 h-4 w-4" />
              </Button>
            </div>
          </div>

          <div className="space-y-6">
            <Card variant="neon">
              <CardContent className="p-6 text-center">
                <Play className="h-8 w-8 text-primary mx-auto mb-4" />
                <h3 className="text-xl font-bold mb-2">Praticar</h3>
                <Link to={path.labUrl || "#"}>
                  <Button variant="glow" className="w-full">INICIAR LAB</Button>
                </Link>
              </CardContent>
            </Card>
            
            <Card variant="glass">
              <CardContent className="p-6">
                <h4 className="text-sm uppercase text-muted-foreground mb-4">Módulos</h4>
                <div className="space-y-2">
                  {modules.map((m, i) => (
                    <div 
                      key={i} 
                      onClick={() => setActiveModule(i)} 
                      className={`p-3 rounded-lg border cursor-pointer transition-all ${
                        activeModule === i 
                        ? 'bg-primary/10 border-primary/30 text-primary' 
                        : 'border-transparent text-muted-foreground hover:bg-white/5'
                      }`}
                    >
                      {i+1}. {m.title}
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>

      {showCertModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-md">
          <Card className="w-full max-w-md border-primary/30 bg-zinc-950">
            <CardHeader className="text-center pt-8">
                <CheckCircle2 className="h-12 w-12 text-primary mx-auto mb-2" />
                <CardTitle>Emissão de Certificado</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4 pb-8">
              <p className="text-center text-sm text-muted-foreground">
                Confirme seu nome para o certificado oficial da trilha <strong>{path.title}</strong>.
              </p>
              <input 
                type="text" 
                value={userNameForCert} 
                onChange={(e) => setUserNameForCert(e.target.value)} 
                className="w-full rounded-lg border border-white/10 bg-white/5 px-4 py-3 text-white focus:outline-none focus:border-primary/50" 
                placeholder="Seu nome completo" 
              />
              <Button 
                variant="glow" 
                className="w-full" 
                onClick={handleIssueCertificate} 
                disabled={isGenerating || !userNameForCert.trim()}
              >
                {isGenerating ? <Loader2 className="animate-spin" /> : "GERAR CERTIFICADO"}
              </Button>
              <Button variant="ghost" className="w-full" onClick={() => setShowCertModal(false)}>
                Cancelar
              </Button>
            </CardContent>
          </Card>
        </div>
      )}
      <Footer />
    </div>
  );
};

export default LearningPathDetails;