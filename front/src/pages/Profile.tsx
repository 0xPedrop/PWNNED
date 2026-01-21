import DashboardSidebar from "@/components/DashboardSidebar";
import DashboardHeader from "@/components/DashboardHeader";
import { Button } from "@/components/ui/button";
import { Badge, Github, Linkedin, Twitter, Mail, MapPin, Calendar, Edit2 } from "lucide-react";
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { Link } from "react-router-dom";

const activityData = [
  { name: 'Mon', hours: 2 },
  { name: 'Tue', hours: 3 },
  { name: 'Wed', hours: 1.5 },
  { name: 'Thu', hours: 4 },
  { name: 'Fri', hours: 3.5 },
  { name: 'Sat', hours: 5 },
  { name: 'Sun', hours: 2 },
];

const achievements = [
  { name: "First Blood", description: "Complete seu primeiro laboratório", earned: true },
  { name: "Bug Hunter", description: "Encontre 10 vulnerabilidades", earned: true },
  { name: "Elite", description: "Complete todos os caminhos difíceis", earned: false },
];

const Profile = () => {
  return (
    <div className="min-h-screen bg-background">
      <DashboardSidebar />
      
      <main className="ml-16 md:ml-64 p-6 md:p-8 transition-all duration-300">
        <DashboardHeader title="Perfil" />

        <div className="grid lg:grid-cols-3 gap-6">
          {/* Profile Card */}
          <div className="lg:col-span-1">
            <div className="glass-card p-6 text-center">
              {/* Avatar */}
              <div className="relative w-28 h-28 mx-auto mb-4">
                <div className="absolute inset-0 rounded-full bg-gradient-to-r from-primary to-secondary blur-md opacity-50" />
                <img
                  src="/Profile.jpg"
                  alt="Perfil"
                  className="relative w-28 h-28 rounded-full object-cover border-2 border-primary/50"
                />
                <Button 
                  variant="ghost" 
                  size="icon" 
                  className="absolute bottom-0 right-0 h-8 w-8 rounded-full bg-card border border-border"
                >
                  <Link to="/dashboard/settings" >
                    <Edit2 className="h-4 w-4" />
                  </Link>
                </Button>
              </div>

              <h2 className="text-xl font-bold text-foreground mb-1">Pedro Paulo</h2>
              <p className="text-primary text-sm mb-2">@0xPedrop</p>
              <p className="text-muted-foreground text-sm mb-4">
                Security researcher & bug bounty hunter. Breaking things to make them better.
              </p>

              {/* Meta */}
              <div className="flex flex-col gap-2 text-sm text-muted-foreground mb-6">
                <div className="flex items-center justify-center gap-2">
                  <MapPin className="h-4 w-4" />
                  <span>Esperança, PB</span>
                </div>
                <div className="flex items-center justify-center gap-2">
                  <Calendar className="h-4 w-4" />
                  <span>Entrou em Janeiro de 2025</span>
                </div>
              </div>

              {/* Social Links */}
              <div className="flex items-center justify-center gap-3">
                <Button variant="ghost" size="icon">
                  <Github className="h-5 w-5" />
                </Button>
                <Button variant="ghost" size="icon">
                  <Linkedin className="h-5 w-5" />
                </Button>
                <Button variant="ghost" size="icon">
                  <Twitter className="h-5 w-5" />
                </Button>
                <Button variant="ghost" size="icon">
                  <Mail className="h-5 w-5" />
                </Button>
              </div>
            </div>

            {/* Stats */}
            <div className="glass-card p-6 mt-6">
              <h3 className="text-lg font-semibold text-foreground mb-4">Status</h3>
              <div className="grid grid-cols-2 gap-4">
                <div className="text-center p-3 rounded-lg bg-muted/30">
                  <div className="text-2xl font-bold text-primary">8</div>
                  <div className="text-xs text-muted-foreground">Cursos</div>
                </div>
                <div className="text-center p-3 rounded-lg bg-muted/30">
                  <div className="text-2xl font-bold text-primary">42</div>
                  <div className="text-xs text-muted-foreground">Labboratórios</div>
                </div>
              </div>
            </div>
          </div>

          {/* Main Content */}
          <div className="lg:col-span-2 space-y-6">
            

            {/* Achievements */}
            <div className="glass-card p-6">
              <h3 className="text-lg font-semibold text-foreground mb-4">Conquistas</h3>
              <div className="grid sm:grid-cols-2 gap-4">
                {achievements.map((achievement) => (
                  <div 
                    key={achievement.name}
                    className={`flex items-center gap-3 p-4 rounded-lg border transition-all ${
                      achievement.earned 
                        ? 'bg-primary/5 border-primary/30' 
                        : 'bg-muted/20 border-border opacity-50'
                    }`}
                  >
                    <div className={`p-2 rounded-full ${achievement.earned ? 'bg-primary/20' : 'bg-muted'}`}>
                      <Badge className={`h-5 w-5 ${achievement.earned ? 'text-primary' : 'text-muted-foreground'}`} />
                    </div>
                    <div>
                      <div className={`font-medium ${achievement.earned ? 'text-foreground' : 'text-muted-foreground'}`}>
                        {achievement.name}
                      </div>
                      <div className="text-xs text-muted-foreground">{achievement.description}</div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Profile;
