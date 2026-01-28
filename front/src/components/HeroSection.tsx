import { Button } from "@/components/ui/button";
import { ArrowRight, Shield, Lock, Terminal } from "lucide-react";
import { Link } from "react-router-dom";

const HeroSection = () => {
  return (
    <section className="relative min-h-screen flex items-center justify-center pt-16">
      <div className="container mx-auto px-4 py-20">
        <div className="max-w-4xl mx-auto text-center">
          {/* Badge */}
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full glass border border-primary/20 mb-8 animate-fade-in">
            <Shield className="h-4 w-4 text-primary" />
            <span className="text-sm text-muted-foreground">
              Aprovado por mais de 100 profissionais de segurança.
            </span>
          </div>

          {/* Main Title */}
          <h1 className="text-5xl md:text-7xl font-bold mb-6 tracking-tight animate-fade-in delay-100">
            <span className="text-foreground">LEARN</span>{" "}
            <span className="gradient-text glow-text">REAL HACKING</span>
            <br />
            <span className="text-foreground">FOR</span>{" "}
            <span className="text-primary">REAL WORLD</span>
          </h1>

          {/* Subtitle */}
          <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto mb-10 animate-fade-in delay-200">
            Domine o hacking com laboratórios práticos, cenários do mundo real e cursos ministrados por especialistas. 
          </p>
          {/* CTA Buttons */}
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-16 animate-fade-in delay-300">
            <Link to="/signup">
              <Button variant="glow" size="xl" className="group">
                Comece Agora
                <ArrowRight className="h-5 w-5 transition-transform group-hover:translate-x-1" />
              </Button>
            </Link>

          </div>


          
        </div>

        {/* Decorative Elements */}
        <div className="absolute top-1/4 left-10 w-64 h-64 bg-primary/10 rounded-full blur-3xl opacity-50" />
        <div className="absolute bottom-1/4 right-10 w-96 h-96 bg-secondary/10 rounded-full blur-3xl opacity-50" />
      </div>
    </section>
  );
};

export default HeroSection;
