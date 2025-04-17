"use client";

import React, { useState, useRef, useEffect } from "react";

function AnimatedText({
  originalText,
  animate,
  tag: Tag = "h1",
  onMouseEnter,
  onMouseLeave,
  gradientColors = ["var(--secondary-400)", "var(--secondary-500)"],
}) {
  const [animatedText, setAnimatedText] = useState(originalText);
  const intervalRef = useRef(null);
  const [isAnimating, setIsAnimating] = useState(animate);

  useEffect(() => {
    const generateRandomText = () => {
      let randomText = "";
      for (let i = 0; i < originalText.length; i++) {
        const characters =
          "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        randomText += characters.charAt(
          Math.floor(Math.random() * characters.length)
        );
      }
      return randomText;
    };

    if (isAnimating) {
      intervalRef.current = setInterval(() => {
        let formattedRandomText = generateRandomText();

        if (Tag === "h1") {
          formattedRandomText = formattedRandomText.toUpperCase();
        } else if (Tag === "p") {
          formattedRandomText = formattedRandomText.toLowerCase();
        }

        setAnimatedText(formattedRandomText);
      }, 100);
    } else {
      clearInterval(intervalRef.current);
      setAnimatedText(originalText);
    }

    return () => clearInterval(intervalRef.current);
  }, [isAnimating, originalText, Tag]);

  const gradientStyle = {
    background: `linear-gradient(to right, ${gradientColors.join(", ")})`,
    WebkitBackgroundClip: "text",
    WebkitTextFillColor: "transparent",
    color: "inherit",
    transition: "color 0.3s ease",
  };

  const defaultStyle = {
    color: "white",
    transition: "color 0.3s ease",
  };

  return (
    <Tag
      onMouseEnter={() => setIsAnimating(true)}
      onMouseLeave={() => setIsAnimating(false)}
      style={!isAnimating ? defaultStyle : {}}
    >
      {animatedText.split("").map((char, index) => (
        <span
          key={index}
          style={isAnimating ? gradientStyle : { color: "inherit" }}
        >
          {char}
        </span>
      ))}
    </Tag>
  );
}

export default AnimatedText;
