"use client";

import React, { useState } from "react";
import Card from "@/ui/components/Card";
import styles from "./ProfileCard.module.css";
import CustomizedImage from "@/ui/components/CustomizedImage";
import Button from "@mui/material/Button";
import AnimatedText from "../AnimatedText";
import { motion } from "framer-motion";

export default function ProfileCard() {
  const [animar, setAnimar] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const iniciarAnimacao = () => {
    setAnimar(true);
  };

  const pararAnimacao = () => {
    setAnimar(false);
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <Card
      width="600px"
      height="450px"
      backgroundColor="#F5F5F5"
      color="#000000"
      shadow=".4rem .4rem 0 #000000"
      border="3px solid #000000"
    >
      <div className={styles.info}>
        <CustomizedImage
          src="/images/profile-photo.png"
          alt="Profile Photo"
          width={90}
          height={90}
          margin="0 20px 0 0"
        />
        <div className={styles.userInfo}>
          <AnimatedText
            originalText="0xJotave"
            animar={animar}
            tag="h1"
            onMouseEnter={iniciarAnimacao}
            onMouseLeave={pararAnimacao}
          />
          <p>eujotave@gmail.com</p>
        </div>
      </div>
      <hr className={styles.separator}></hr>
      <form className={styles.formGroup}>
        <div className={styles.formInfo}>
          <label>Username</label>
          <input type="text" className="username" placeholder="0xJotave" />
        </div>
        <hr className={styles.separator}></hr>
        <div className={styles.formInfo}>
          <label>Email</label>
          <input
            type="text"
            className="email"
            placeholder="eujotave@gmail.com"
          />
        </div>
        <hr className={styles.separator}></hr>
        <div className={styles.formInfo}>
          <label>Password</label>
          <div className={styles.passwordInputContainer}>
            <input
              type={showPassword ? "text" : "password"}
              className="password"
              placeholder="*******"
              style={{ width: "100%" }}
            />
            <motion.button
              type="button"
              className={styles.passwordToggleButton}
              onClick={togglePasswordVisibility}
              whileTap={{ scale: 1 }}
            >
              {showPassword ? (
                <img
                  src="/open-eye.png"
                  alt="Hide password"
                  width={20}
                  height={20}
                />
              ) : (
                <img
                  src="/closed-eye.png"
                  alt="Show password"
                  width={20}
                  height={20}
                  style={{ marginTop: "0.3rem" }}
                />
              )}
            </motion.button>
          </div>
        </div>
        <Button
          variant="contained"
          disableElevation
          sx={{
            backgroundColor: "#013220",
            "&:hover": {
              backgroundColor: "#0B6E4F",
            },
            marginTop: "15px",
            alignSelf: "flex-end",
            padding: "8px",
            width: "150px",
            height: "40px",
          }}
        >
          Save Changes
        </Button>
      </form>
    </Card>
  );
}
