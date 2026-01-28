import { Github, Linkedin, Twitter } from "lucide-react";

const team = [
  {
    name: "Pedro Paulo (0xPedrop)",
    role: "Founder, Tech Lead Dev & Security Researcher",
    image: "/Profile.jpg",
    bio: "Bug bounty hunter with $10k+ in earnings, 2 CVEs",
    socials: {
      linkedin: "https://www.linkedin.com/in/0xpedrop/",
      github: "https://github.com/0xPedrop",
    },
  },
  {
    name: "João Victor",
    role: "Founder & Tech Lead Dev",
    image: "/Joao.jpeg",
    bio: "Fullstack developer and cybersecurity enthusiast",
    socials: {
      linkedin: "https://www.linkedin.com/in/0xjotave/",
      github: "https://github.com/0xJotave"
    },
  },
  {
    name: "Wolgrand Araújo",
    role: "Developer",
    image: "/Wolgrand.jpeg",
    bio: "Ide.ia Developer",
    socials: {
      linkedin: "https://www.linkedin.com/in/wolgrand-araujop/",
      github: "https://github.com/WolgrandAP",
    },
  },

];

const TeamSection = () => {
  return (
    <section id="team" className="relative py-24">
      <div className="container mx-auto px-4">
        {/* Header */}
        <div className="text-center max-w-2xl mx-auto mb-16">
          <h2 className="text-4xl md:text-5xl font-bold mb-4">
            <span className="text-foreground">Conheça os </span>
            <span className="gradient-text">Especialistas</span>
          </h2>
          <p className="text-lg text-muted-foreground">
            Aprenda com veteranos da indústria que já estiveram na linha de frente. Hackers de verdade ensinando habilidades reais.
          </p>
        </div>

        {/* Team Grid */}
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-6xl mx-auto">
          {team.map((member, index) => (
            <div
              key={member.name}
              className="group glass-card p-6 text-center transition-all duration-500 hover:border-primary/30 animate-fade-in"
              style={{ animationDelay: `${index * 100}ms` }}
            >
              {/* Avatar */}
              <div className="relative w-28 h-28 mx-auto mb-5">
                <div className="absolute inset-0 rounded-full bg-gradient-to-r from-primary to-secondary opacity-0 group-hover:opacity-100 transition-opacity duration-500 blur-md" />
                <img
                  src={member.image}
                  alt={member.name}
                  className="relative w-28 h-28 rounded-full object-cover border-2 border-border group-hover:border-primary/50 transition-colors duration-500"
                />
              </div>

              {/* Info */}
              <h3 className="text-lg font-semibold text-foreground mb-1">{member.name}</h3>
              <p className="text-sm text-primary mb-3">{member.role}</p>
              <p className="text-sm text-muted-foreground mb-5 line-clamp-2">{member.bio}</p>

              {/* Social Links */}
              <div className="flex items-center justify-center gap-4 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                <a
                  href={member.socials.linkedin}
                  className="text-muted-foreground hover:text-primary transition-colors"
                  aria-label={`${member.name}'s LinkedIn`}
                >
                  <Linkedin className="h-5 w-5" />
                </a>
                <a
                  href={member.socials.github}
                  className="text-muted-foreground hover:text-primary transition-colors"
                  aria-label={`${member.name}'s GitHub`}
                >
                  <Github className="h-5 w-5" />
                </a>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default TeamSection;
