import { useState } from "react";
// import { Button } from "@/components/ui/button";
// import { Input } from "@/components/ui/input";
import { Terminal, Github, Twitter, Linkedin, Youtube, Send } from "lucide-react";
import { Link } from "react-router-dom";

const Footer = () => {
  /* COMENTADO: Lógica de Newsletter
  const [email, setEmail] = useState("");
  const [subscribed, setSubscribed] = useState(false);

  const handleSubscribe = (e: React.FormEvent) => {
    e.preventDefault();
    if (email) {
      setSubscribed(true);
      setEmail("");
    }
  };
  */

  const links = {
    platform: [
      { name: "Caminhos de aprendizagem", href: "#" },
      { name: "Laboratórios", href: "#" },
      { name: "Certificações", href: "#" },
      { name: "Desafios CTF", href: "#" },
    ],
    resources: [
      { name: "Documentação", href: "#" },
      { name: "Blog", href: "#" },
      { name: "Comunidade", href: "#" },
      { name: "API Reference", href: "#" },
    ],
    company: [
      { name: "Sobre Nós", href: "#" },
      { name: "Carreiras", href: "#" },
      { name: "Contato", href: "#" },
      { name: "Política de privacidade", href: "#" },
    ],
  };

  return (
    <footer className="relative border-t border-border bg-card/30">
      {/* COMENTADO: Newsletter Section 
      <div className="border-b border-border">
        <div className="container mx-auto px-4 py-16">
          <div className="max-w-2xl mx-auto text-center">
            <h3 className="text-2xl md:text-3xl font-bold mb-3">
              <span className="text-foreground">Antecipe </span>
              <span className="text-foreground">as</span>
              <span className="gradient-text"> ameaças</span>
            </h3>
            <p className="text-muted-foreground mb-8">
              Receba informações semanais sobre segurança, anúncios de novos cursos e conteúdo exclusivo.
            </p>

            {subscribed ? (
              <div className="glass-card p-6 animate-scale-in">
                <div className="flex items-center justify-center gap-2 text-primary">
                  <Terminal className="h-5 w-5" />
                  <span className="font-medium">Bem vindo(a) à rede!</span>
                </div>
                <p className="text-sm text-muted-foreground mt-2">
                  Verifique sua caixa de entrada para encontrar um e-mail de confirmação.
                </p>
              </div>
            ) : (
              <form onSubmit={handleSubscribe} className="flex flex-col sm:flex-row gap-3 max-w-md mx-auto">
                <Input
                  type="email"
                  placeholder="Enter your email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="flex-1"
                  required
                />
                <Button type="submit" variant="glow" className="group">
                  Inscrever-se
                  <Send className="h-4 w-4 transition-transform group-hover:translate-x-1" />
                </Button>
              </form>
            )}
          </div>
        </div>
      </div>
      */}

      {/* Main Footer */}
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-2 md:grid-cols-5 gap-8">
          {/* Brand */}
          <div className="col-span-2">
            <Link to="/" className="flex items-center gap-2 mb-4">
              <Terminal className="h-7 w-7 text-primary" />
              <span className="text-xl font-bold">
                <span className="text-primary">Pwn</span>
                <span className="text-foreground">ned</span>
              </span>
            </Link>
            <p className="text-sm text-muted-foreground mb-6 max-w-xs">
              A melhor plataforma para educação em hacking ético. Aprenda habilidades práticas com especialistas do setor.
            </p>
            <div className="flex items-center gap-4">
              <a href="#" className="text-muted-foreground hover:text-primary transition-colors">
                <Github className="h-5 w-5" />
              </a>
              <a href="#" className="text-muted-foreground hover:text-primary transition-colors">
                <Twitter className="h-5 w-5" />
              </a>
              <a href="#" className="text-muted-foreground hover:text-primary transition-colors">
                <Linkedin className="h-5 w-5" />
              </a>
              <a href="#" className="text-muted-foreground hover:text-primary transition-colors">
                <Youtube className="h-5 w-5" />
              </a>
            </div>
          </div>

          {/* Links */}
          <div>
            <h4 className="font-semibold text-foreground mb-4">Plataforma</h4>
            <ul className="space-y-3">
              {links.platform.map((link) => (
                <li key={link.name}>
                  <a href={link.href} className="text-sm text-muted-foreground hover:text-primary transition-colors">
                    {link.name}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          <div>
            <h4 className="font-semibold text-foreground mb-4">Recursos</h4>
            <ul className="space-y-3">
              {links.resources.map((link) => (
                <li key={link.name}>
                  <a href={link.href} className="text-sm text-muted-foreground hover:text-primary transition-colors">
                    {link.name}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          <div>
            <h4 className="font-semibold text-foreground mb-4">Empresa</h4>
            <ul className="space-y-3">
              {links.company.map((link) => (
                <li key={link.name}>
                  <a href={link.href} className="text-sm text-muted-foreground hover:text-primary transition-colors">
                    {link.name}
                  </a>
                </li>
              ))}
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="border-t border-border mt-12 pt-8 flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-sm text-muted-foreground">
            © 2026 Pwnned. All rights reserved.
          </p>
          <div className="flex items-center gap-6 text-sm text-muted-foreground">
            <a href="#" className="hover:text-primary transition-colors">Termos</a>
            <a href="#" className="hover:text-primary transition-colors">Privacidade</a>
            <a href="#" className="hover:text-primary transition-colors">Cookies</a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;