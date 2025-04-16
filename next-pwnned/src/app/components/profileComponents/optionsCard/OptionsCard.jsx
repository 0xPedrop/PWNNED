"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import Card from "@/ui/components/Card";
import styles from "./OptionsCard.module.css";
import CustomizedImage from "@/ui/components/CustomizedImage";
import Image from "next/image";
import SettingsCard from "../settingsCard/SettingsCard";
import ProfileCard from "../profileCard/ProfileCard";
import { motion, AnimatePresence } from "framer-motion";

export default function OptionsCard() {
  const [cardState, setCardState] = useState({
    profile: { open: true, color: true },
    settings: { open: false, color: false },
    logout: { open: false, color: false },
  });

  const handleCardClick = (cardName) => {
    setCardState((prevState) => {
      const newState = {};
      Object.keys(prevState).forEach((key) => {
        newState[key] = { open: false, color: false };
      });
      newState[cardName] = {
        open: !prevState[cardName].open,
        color: !prevState[cardName].color,
      };
      return newState;
    });
  };

  const router = useRouter();

  const handleLogout = () => {
    router.push("/");
  };

  return (
    <>
      <Card
        width="14rem"
        height="14rem"
        backgroundColor="var(--background-75)"
        shadow="0px 5px 15px var(--background-100)"
        color="var(--text-900)"
        borderRadius="5px"
        className={styles.optionsCard}
      >
        <div className={styles.info}>
          <CustomizedImage
            src="/images/profile-photo.png"
            alt="Profile Photo"
            width={60}
            height={60}
            margin="0 10px 0 0"
          />
          <div className={styles.userInfo}>
            <h1>0xJotave</h1>
            <p>eujotave@gmail.com</p>
          </div>
        </div>
        <hr className={styles.separator}></hr>
        <div className={styles.options}>
          <div
            className={styles.myProfile}
            onClick={() => handleCardClick("profile")}
            style={{
              backgroundColor: cardState.profile.color
                ? "var(--background-400)"
                : "",
            }}
          >
            <div className={styles.iconNameContainer}>
              <Image
                src="/user.png"
                alt="Profile Icon"
                width={25}
                height={25}
                style={{
                  objectFit: "contain",
                  marginRight: "10px",
                }}
              />
              <a>My Profile</a>
            </div>
            <Image
              src="/next-white.png"
              alt="Profile Icon"
              width={20}
              height={20}
              style={{ objectFit: "contain" }}
            />
          </div>
          <div
            className={styles.settings}
            onClick={() => handleCardClick("settings")}
            style={{
              backgroundColor: cardState.settings.color
                ? "var(--background-400)"
                : "",
            }}
          >
            <div className={styles.iconNameContainer}>
              <Image
                src="/settings.png"
                alt="Settings Icon"
                width={25}
                height={25}
                style={{
                  objectFit: "contain",
                  marginRight: "10px",
                }}
              />
              <a>Settings</a>
            </div>
            <Image
              src="/next-white.png"
              alt="Profile Icon"
              width={20}
              height={20}
              style={{ objectFit: "contain" }}
            />
          </div>
          <div
            className={styles.logout}
            onClick={handleLogout}
            style={{
              backgroundColor: cardState.logout.color
                ? "var(--background-400)"
                : "",
            }}
          >
            <div className={styles.iconNameContainer}>
              <Image
                src="/logout.png"
                alt="Logout Icon"
                width={25}
                height={25}
                style={{
                  objectFit: "contain",
                  marginRight: "10px",
                }}
              />
              <a>Log Out</a>
            </div>
            <Image
              src="/next-white.png"
              alt="Profile Icon"
              width={20}
              height={20}
              style={{ objectFit: "contain" }}
            />
          </div>
        </div>
      </Card>

      <AnimatePresence mode="wait">
        {cardState.settings.open && (
          <motion.div
            key="settings"
            initial={{ opacity: 0, x: 0 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: 0 }}
            transition={{ duration: 0.2 }}
          >
            <SettingsCard />
          </motion.div>
        )}
        {cardState.profile.open && (
          <motion.div
            key="profile"
            style={{ marginLeft: "2.5 em" }}
            initial={{ opacity: 0, x: 0 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: 0 }}
            transition={{ duration: 0.2 }}
          >
            <ProfileCard />
          </motion.div>
        )}
      </AnimatePresence>
    </>
  );
}
