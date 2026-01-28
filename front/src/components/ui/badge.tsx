import * as React from "react"
import { cva, type VariantProps } from "class-variance-authority"

import { cn } from "@/lib/utils"

const badgeVariants = cva(
  "inline-flex items-center rounded-full border px-2.5 py-0.5 text-xs font-semibold transition-colors focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2",
  {
    variants: {
      variant: {
        default:
          "border-transparent bg-primary text-primary-foreground hover:bg-primary/80",
        secondary:
          "border-transparent bg-secondary text-secondary-foreground hover:bg-secondary/80",
        destructive:
          "border-transparent bg-destructive text-destructive-foreground hover:bg-destructive/80",
        outline: "text-foreground",
        
        // --- Novas Variantes Adicionadas (Correção do Erro) ---
        glow: 
          "border-primary/50 bg-primary/10 text-primary shadow-[0_0_10px_rgba(6,182,212,0.3)] hover:shadow-[0_0_15px_rgba(6,182,212,0.5)] transition-all",
        
        // Variantes de Dificuldade
        beginner: 
          "border-emerald-500/30 bg-emerald-500/10 text-emerald-500 hover:bg-emerald-500/20",
        intermediate: 
          "border-amber-500/30 bg-amber-500/10 text-amber-500 hover:bg-amber-500/20",
        advanced: 
          "border-red-500/30 bg-red-500/10 text-red-500 hover:bg-red-500/20",
        elite: 
          "border-purple-500/30 bg-purple-500/10 text-purple-500 hover:bg-purple-500/20",
      },
    },
    defaultVariants: {
      variant: "default",
    },
  }
)

export interface BadgeProps
  extends React.HTMLAttributes<HTMLDivElement>,
    VariantProps<typeof badgeVariants> {}

function Badge({ className, variant, ...props }: BadgeProps) {
  return (
    <div className={cn(badgeVariants({ variant }), className)} {...props} />
  )
}

export { Badge, badgeVariants }