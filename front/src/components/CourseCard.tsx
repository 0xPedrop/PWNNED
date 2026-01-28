import { Link } from "react-router-dom";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Progress } from "@/components/ui/progress";
import { Clock, Users, Zap, ArrowRight, BarChart3 } from "lucide-react";

const CourseCard = ({
  id,
  title,
  description,
  difficulty = "Medium",
  xp = 0,
  duration,
  students = 0,
  progress = 0, 
  image = null,
}) => {
  
  const normalizedDifficulty = difficulty.toLowerCase();

  const getDifficultyColor = (diff) => {
    if (diff.includes("easy") || diff.includes("beginner")) return "from-emerald-500/20 to-emerald-600/10 border-emerald-500/20";
    if (diff.includes("medium") || diff.includes("intermediate")) return "from-amber-500/20 to-amber-600/10 border-amber-500/20";
    if (diff.includes("hard") || diff.includes("advanced")) return "from-red-500/20 to-red-600/10 border-red-500/20";
    if (diff.includes("elite")) return "from-purple-500/20 to-purple-600/10 border-purple-500/20";
    return "from-primary/20 to-primary/10";
  };

  const getBadgeVariant = (diff) => {
    if (diff.includes("easy") || diff.includes("beginner")) return "bg-emerald-500/10 text-emerald-500 hover:bg-emerald-500/20 border-emerald-500/20";
    if (diff.includes("medium") || diff.includes("intermediate")) return "bg-amber-500/10 text-amber-500 hover:bg-amber-500/20 border-amber-500/20";
    if (diff.includes("hard") || diff.includes("advanced")) return "bg-red-500/10 text-red-500 hover:bg-red-500/20 border-red-500/20";
    if (diff.includes("elite")) return "bg-purple-500/10 text-purple-500 hover:bg-purple-500/20 border-purple-500/20";
    return "secondary";
  };

  const gradientClass = getDifficultyColor(normalizedDifficulty);
  const badgeClass = getBadgeVariant(normalizedDifficulty);

  return (
    <Card variant="glassHover" className="group h-full overflow-hidden flex flex-col">
      <div className={`h-32 bg-gradient-to-br ${gradientClass} relative overflow-hidden`}>
        {image ? (
          <img src={image} alt={title} className="absolute inset-0 w-full h-full object-cover opacity-50 mix-blend-overlay" />
        ) : (
           <div className="absolute inset-0 flex items-center justify-center opacity-10">
             <BarChart3 className="w-16 h-16" />
           </div>
        )}
        
        <div className="absolute top-3 left-3 flex gap-2">
          <Badge className={`backdrop-blur-md border ${badgeClass}`}>
            {difficulty.charAt(0).toUpperCase() + difficulty.slice(1)}
          </Badge>
        </div>
        
        {/* REMOVIDO: Badge de XP que aparecia sobre a imagem do card */}
        {/* <div className="absolute top-3 right-3">
          <Badge variant="outline" className="flex items-center gap-1 bg-black/40 backdrop-blur-md border-white/10 text-primary animate-pulse-slow">
            <Zap className="h-3 w-3 fill-primary" />
            {xp} XP
          </Badge>
        </div> 
        */}
      </div>

      <CardHeader className="pb-2 pt-4">
        <CardTitle className="text-lg line-clamp-1 group-hover:text-primary transition-colors">
          {title}
        </CardTitle>
      </CardHeader>

      <CardContent className="space-y-4 flex-grow">
        <p className="text-sm text-muted-foreground line-clamp-2 min-h-[2.5rem]">
          {description}
        </p>

        {progress > 0 ? (
          <div className="space-y-1.5">
            <div className="flex justify-between text-xs text-muted-foreground">
              <span>Progress</span>
              <span className={progress === 100 ? "text-emerald-500" : "text-primary"}>{progress}%</span>
            </div>
            <Progress value={progress} className="h-1.5 bg-muted/20" />
          </div>
        ) : (
           <div className="h-1.5" />
        )}

        <div className="flex items-center justify-between text-xs text-muted-foreground pt-2 border-t border-white/5">
          <div className="flex items-center gap-1.5">
            <Clock className="h-3.5 w-3.5" />
            {duration}
          </div>
          <div className="flex items-center gap-1.5">
            <Users className="h-3.5 w-3.5" />
            {students.toLocaleString()} enrolled
          </div>
        </div>
      </CardContent>

      <CardFooter className="pt-0 pb-5">
        <Link to={`/dashboard/learningpaths/${id}`} className="w-full">
          <Button 
            variant="outline" 
            className="w-full border-white/10 hover:bg-primary/10 hover:border-primary/50 hover:text-primary group-hover/btn:translate-x-1 transition-all"
          >
            {progress > 0 ? "Continue Learning" : "Start Path"}
            <ArrowRight className="h-4 w-4 ml-2 transition-transform group-hover:translate-x-1" />
          </Button>
        </Link>
      </CardFooter>
    </Card>
  );
};

export default CourseCard;