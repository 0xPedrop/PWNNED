"use client";

import { useEffect, useRef, useState } from "react";
import styles from "./Hero.module.css";

const Hero = () => {
  const animationRef = useRef(null);
  const [randomText, setRandomText] = useState("");
  const [mouseMoved, setMouseMoved] = useState(false);

  const generateRandomText = () => {
    const chars =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    const randomCharacter = () =>
      chars[Math.floor(Math.random() * chars.length)];
    const randomString = (length) =>
      Array.from({ length }).map(randomCharacter).join(" ");

    setRandomText(randomString(8000));
  };

  useEffect(() => {
    generateRandomText(); // Gerar texto inicial

    const handleMouseMove = (e) => {
      if (animationRef.current) {
        animationRef.current.style.setProperty("--x", `${e.clientX}px`);
        animationRef.current.style.setProperty("--y", `${e.clientY}px`);
        if (!mouseMoved) {
          setMouseMoved(true);
          generateRandomText();
        } else {
          generateRandomText();
        }
      }
    };

    document.addEventListener("mousemove", handleMouseMove);
    return () => document.removeEventListener("mousemove", handleMouseMove);
  }, [mouseMoved]); // Dependencia no mouseMoved para executar generateRandomText apenas quando o mouse é movido.

  return (
    <>
      <div className="animationContainer" ref={animationRef}>
        {mouseMoved ? randomText : ""}
      </div>
      <section className={styles.hero}>
        <div className={styles.content}>
          <h1>
            LEARN REAL HACKING
            <br />
            FOR REAL WORLD<span className={styles.cursor}>_</span>
          </h1>
        </div>
      </section>
    </>
  );
};

export default Hero;
