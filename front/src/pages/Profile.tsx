import DashboardSidebar from "@/components/DashboardSidebar";
import DashboardHeader from "@/components/DashboardHeader";
import { Button } from "@/components/ui/button";
import { Github, Linkedin, Twitter, Mail, MapPin, Calendar, Edit2 } from "lucide-react";
import { Link } from "react-router-dom";

const Profile = () => {
  return (
    <div className="min-h-screen bg-background">
      <DashboardSidebar />
      
      <main className="ml-16 md:ml-64 p-6 md:p-8 transition-all duration-300">
        <DashboardHeader title="Perfil" />

        <div className="max-w-4xl mx-auto">
          {/* Profile Card */}
          <div className="glass-card p-8 text-center">
            {/* Avatar */}
            <div className="relative w-32 h-32 mx-auto mb-6">
              <div className="absolute inset-0 rounded-full bg-gradient-to-r from-primary to-secondary blur-md opacity-50" />
              <img
                src="/Profile.jpg"
                alt="Perfil"
                className="relative w-32 h-32 rounded-full object-cover border-2 border-primary/50"
              />
              <Button 
                variant="ghost" 
                size="icon" 
                className="absolute bottom-0 right-0 h-10 w-10 rounded-full bg-card border border-border"
                asChild
              >
                <Link to="/dashboard/settings" >
                  <Edit2 className="h-5 w-5" />
                </Link>
              </Button>
            </div>

            <h2 className="text-2xl font-bold text-foreground mb-1">Pedro Paulo</h2>
            <p className="text-primary font-medium mb-4">@0xPedrop</p>
            
            <p className="max-w-xl mx-auto text-muted-foreground text-sm mb-8">
              Security researcher & bug bounty hunter. Breaking things to make them better.
            </p>

            {/* Meta Info */}
            <div className="flex flex-wrap items-center justify-center gap-6 text-sm text-muted-foreground mb-8">
              <div className="flex items-center gap-2">
                <MapPin className="h-4 w-4 text-primary" />
                <span>Esperança, PB</span>
              </div>
              <div className="flex items-center gap-2">
                <Calendar className="h-4 w-4 text-primary" />
                <span>Entrou em Janeiro de 2025</span>
              </div>
            </div>

            {/* Social Links */}
            <div className="flex items-center justify-center gap-4">
              <Button variant="outline" size="icon" className="rounded-full">
                <Github className="h-5 w-5" />
              </Button>
              <Button variant="outline" size="icon" className="rounded-full">
                <Linkedin className="h-5 w-5" />
              </Button>
              <Button variant="outline" size="icon" className="rounded-full">
                <Twitter className="h-5 w-5" />
              </Button>
              <Button variant="outline" size="icon" className="rounded-full">
                <Mail className="h-5 w-5" />
              </Button>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Profile;