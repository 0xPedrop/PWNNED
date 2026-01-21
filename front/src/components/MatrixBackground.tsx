import { useEffect, useRef, useState, useCallback } from "react";

const MatrixBackground = () => {
  const containerRef = useRef<HTMLDivElement>(null);
  const [randomText, setRandomText] = useState("");
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });
  const [isActive, setIsActive] = useState(false);

  const chars = "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@#$%^&*(){}[]|;:',.<>?/\\~`!";

  const generateRandomText = useCallback(() => {
    const randomCharacter = () => chars[Math.floor(Math.random() * chars.length)];
    const randomString = (length: number) => 
      Array.from({ length }).map(randomCharacter).join(" ");
    setRandomText(randomString(10000));
  }, []);

  useEffect(() => {
    generateRandomText();
    
    const handleMouseMove = (e: MouseEvent) => {
      setMousePosition({ x: e.clientX, y: e.clientY });
      setIsActive(true);
      generateRandomText();
    };

    const handleMouseLeave = () => {
      setIsActive(false);
    };

    document.addEventListener("mousemove", handleMouseMove);
    document.addEventListener("mouseleave", handleMouseLeave);

    return () => {
      document.removeEventListener("mousemove", handleMouseMove);
      document.removeEventListener("mouseleave", handleMouseLeave);
    };
  }, [generateRandomText]);

  return (
    <div 
      ref={containerRef}
      className="fixed inset-0 z-0 overflow-hidden pointer-events-none"
      style={{
        background: `radial-gradient(600px circle at ${mousePosition.x}px ${mousePosition.y}px, rgba(34, 211, 238, 0.06), transparent 40%)`
      }}
    >
      {/* Matrix characters */}
      <div 
        className={`absolute inset-0 matrix-text transition-opacity duration-500 ${isActive ? 'opacity-100' : 'opacity-0'}`}
        style={{
          maskImage: `radial-gradient(400px circle at ${mousePosition.x}px ${mousePosition.y}px, black 0%, transparent 70%)`,
          WebkitMaskImage: `radial-gradient(400px circle at ${mousePosition.x}px ${mousePosition.y}px, black 0%, transparent 70%)`
        }}
      >
        {randomText}
      </div>

      {/* Gradient overlay */}
      <div className="absolute inset-0 bg-gradient-to-b from-transparent via-background/50 to-background pointer-events-none" />
      
      {/* Grid pattern */}
      <div 
        className="absolute inset-0 opacity-[0.02]"
        style={{
          backgroundImage: `
            linear-gradient(rgba(34, 211, 238, 0.3) 1px, transparent 1px),
            linear-gradient(90deg, rgba(34, 211, 238, 0.3) 1px, transparent 1px)
          `,
          backgroundSize: '50px 50px'
        }}
      />
    </div>
  );
};

export default MatrixBackground;
