import MatrixBackground from "@/components/MatrixBackground";
import Navbar from "@/components/Navbar";
import HeroSection from "@/components/HeroSection";
import PricingSection from "@/components/PricingSection";
import TeamSection from "@/components/TeamSection";
import Footer from "@/components/Footer";

const Index = () => {
  return (
    <div className="min-h-screen bg-background text-foreground overflow-x-hidden">
      <MatrixBackground />
      <Navbar />
      <main className="relative z-10">
        <HeroSection />
        <PricingSection />
        <TeamSection />
      </main>
      <Footer />
    </div>
  );
};

export default Index;
