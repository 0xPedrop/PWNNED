import { Button } from "@/components/ui/button";
import { Check, Zap } from "lucide-react";

const plans = [
  {
    name: "Free",
    price: "R$0",
    period: "/mês",
    description: "Ideal para iniciantes que desejam explorar a cibersegurança.",
    features: [
      "10 Caminhos de aprendizagem",
      "50 laboratórios práticos",
      "Acesso à comunidade",
      "Certificados básicos",
      "Suporte por email",
    ],
    popular: false,
  },
  {
    name: "Premium",
    price: "R$10",
    period: "/mês",
    description: "Para alunos dedicados prontos para avançar de nível.",
    features: [
      "Todos os mais de 50 caminhos de aprendizagem",
      "Mais de 200 laboratórios práticos",
      "Live Workshops",
      "Certificados PRO",
      "Suporte prioritário",
      "Acesso ao discord privado",
    ],
    popular: true,
  },

];

const PricingSection = () => {
  return (
    <section id="pricing" className="relative py-24">
      <div className="container mx-auto px-4">
        {/* Header */}
        <div className="text-center max-w-2xl mx-auto mb-16">
          <h2 className="text-4xl md:text-5xl font-bold mb-4">
            <span className="text-foreground">Preço </span>
            <span className="text-foreground">Transparente</span>
            <span className="gradient-text"> e Acessível</span>
          </h2>

        </div>

        {/* Pricing Cards */}
        <div className="grid md:grid-cols-2 gap-8 max-w-6xl mx-auto">
          {plans.map((plan, index) => (
            <div
              key={plan.name}
              className={`relative rounded-2xl p-8 transition-all duration-500 animate-fade-in ${
                plan.popular
                  ? "glass-card neon-border scale-105 z-10"
                  : "glass-card hover:border-primary/30"
              }`}
              style={{ animationDelay: `${index * 100}ms` }}
            >
              {/* Popular Badge */}
              {plan.popular && (
                <div className="absolute -top-4 left-1/2 -translate-x-1/2">
                  <div className="flex items-center gap-1.5 px-4 py-1.5 rounded-full bg-gradient-to-r from-primary to-secondary text-primary-foreground text-sm font-medium shadow-glow">
                    <Zap className="h-4 w-4" />
                    Mais Popular
                  </div>
                </div>
              )}

              {/* Plan Header */}
              <div className="text-center mb-8">
                <h3 className="text-2xl font-bold text-foreground mb-2">{plan.name}</h3>
                <p className="text-muted-foreground text-sm mb-4">{plan.description}</p>
                <div className="flex items-baseline justify-center gap-1">
                  <span className={`text-5xl font-bold ${plan.popular ? 'gradient-text' : 'text-foreground'}`}>
                    {plan.price}
                  </span>
                  <span className="text-muted-foreground">{plan.period}</span>
                </div>
              </div>

              {/* Features */}
              <ul className="space-y-4 mb-8">
                {plan.features.map((feature) => (
                  <li key={feature} className="flex items-center gap-3">
                    <div className={`flex-shrink-0 w-5 h-5 rounded-full flex items-center justify-center ${
                      plan.popular ? 'bg-primary/20' : 'bg-muted'
                    }`}>
                      <Check className={`h-3 w-3 ${plan.popular ? 'text-primary' : 'text-muted-foreground'}`} />
                    </div>
                    <span className="text-muted-foreground text-sm">{feature}</span>
                  </li>
                ))}
              </ul>

              {/* CTA Button */}
              <Button
                variant={plan.popular ? "glow" : "outline"}
                className="w-full"
                size="lg"
              >
                Inscreva agora
              </Button>
            </div>
          ))}
        </div>
      </div>

      {/* Background decoration */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-primary/5 rounded-full blur-3xl opacity-50 pointer-events-none" />
    </section>
  );
};

export default PricingSection;
