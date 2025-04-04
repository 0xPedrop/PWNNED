"use client";

import { useEffect, useRef, useState } from "react";
import Image from "next/image";
import styles from "./Hero.module.css";

const Hero = () => {
  const animationRef = useRef(null);
  const [randomText, setRandomText] = useState("");

  const generateRandomText = () => {
    const chars =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    const randomCharacter = () =>
      chars[Math.floor(Math.random() * chars.length)];
    const randomString = (length) =>
      Array.from({ length }).map(randomCharacter).join(" ");

    setRandomText(randomString(9000));
  };

  useEffect(() => {
    generateRandomText(); // Gerar texto inicial

    const handleMouseMove = (e) => {
      if (animationRef.current) {
        animationRef.current.style.setProperty("--x", `${e.clientX}px`);
        animationRef.current.style.setProperty("--y", `${e.clientY}px`);
        generateRandomText(); // Gerar novo texto ao mover o mouse
      }
    };

    document.addEventListener("mousemove", handleMouseMove);
    return () => document.removeEventListener("mousemove", handleMouseMove);
  }, []);

  return (
    <>
      <div className="animationContainer" ref={animationRef}>
        {randomText}
      </div>
      <section className={styles.hero}>
        <div className={styles.content}>
          <h1>
            LEARN REAL HACKING
            <br />
            FOR REAL WORLD<span className={styles.cursor}>_</span>
          </h1>
        </div>
        <div className={styles.imageContainer}>
          <Image
            src="/hero-image.png"
            alt="Hacker working"
            width={500}
            height={500}
          />
        </div>
      </section>
    </>
  );
};

export default Hero;
