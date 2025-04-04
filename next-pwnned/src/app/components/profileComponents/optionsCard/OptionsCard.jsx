'use client';

import React, { useState } from 'react';
import Card from "@/ui/components/Card";
import styles from "./OptionsCard.module.css";
import CustomizedImage from "@/ui/components/CustomizedImage";
import Image from 'next/image';
import SettingsCard from '../settingsCard/SettingsCard';
import ProfileCard from '../profileCard/ProfileCard';
import { motion, AnimatePresence } from "framer-motion";

export default function OptionsCard() {
    const [openCards, setOpenCards] = useState({
        profile: true,
        settings: false,
        logout: false,
    });

    const [optionColors, setOptionColors] = useState({
        profile: true,
        settings: false,
        logout: false,
    });

    const handleCardClick = (cardName) => {
        setOpenCards((prevOpenCards) => {
            if (prevOpenCards[cardName]) {
                return {
                    profile: false,
                    settings: false,
                    logout: false,
                };
            }

            const newOpenCards = {
                profile: false,
                settings: false,
                logout: false,
                [cardName]: true,
            };
            return newOpenCards;
        });

        // Alterna o estado de seleção do card clicado
        setOptionColors((prevColors) => {
            const updatedColors = {
                profile: false,
                settings: false,
                logout: false,
                [cardName]: !prevColors[cardName], // Inverte o estado
            };
            return updatedColors;
        });
    };

    return (
        <>
            <Card 
                width="250px" 
                height="270px" 
                backgroundColor="#F5F5F5"
                color="#000000"
                shadow=".3rem .3rem 0 #000000"
                border="3px solid #000000"
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
                        onClick={() => handleCardClick('profile')}
                        style={{ backgroundColor: optionColors.profile ? 'rgba(0, 100, 50, 0.589)' : '' }}
                    >
                        <div className={styles.iconNameContainer}>
                            <Image 
                                src="/user.png" 
                                alt="Profile Icon" 
                                width={25} 
                                height={25} 
                                style={{ 
                                    objectFit: 'contain', 
                                    marginRight: '10px'
                                }}
                            />
                            <a>My Profile</a>
                        </div>
                        <Image 
                            src="/next.png" 
                            alt="Profile Icon" 
                            width={20} 
                            height={20} 
                            style={{ objectFit: 'contain' }}
                        />
                    </div>
                    <div 
                        className={styles.settings} 
                        onClick={() => handleCardClick('settings')}
                        style={{ backgroundColor: optionColors.settings ? 'rgba(0, 100, 50, 0.589)' : '' }}
                    >
                        <div className={styles.iconNameContainer}>
                            <Image 
                                src="/settings.png" 
                                alt="Settings Icon" 
                                width={25} 
                                height={25} 
                                style={{ 
                                    objectFit: 'contain', 
                                    marginRight: '10px'
                                }}
                            />
                            <a>Settings</a>
                        </div>
                        <Image 
                            src="/next.png" 
                            alt="Profile Icon" 
                            width={20} 
                            height={20} 
                            style={{ objectFit: 'contain' }}
                        />
                    </div>
                    <div 
                        className={styles.logout} 
                        onClick={() => handleCardClick('logout')}
                        style={{ backgroundColor: optionColors.logout ? 'rgba(0, 100, 50, 0.589)' : '' }}
                    >
                        <div className={styles.iconNameContainer}>
                            <Image 
                                src="/logout.png" 
                                alt="Logout Icon" 
                                width={25} 
                                height={25} 
                                style={{ 
                                    objectFit: 'contain', 
                                    marginRight: '10px'
                                }}
                            />
                            <a>Log Out</a>
                        </div>
                        <Image 
                            src="/next.png" 
                            alt="Profile Icon" 
                            width={20} 
                            height={20} 
                            style={{ objectFit: 'contain' }}
                        />
                    </div>
                </div>
            </Card>

            <AnimatePresence mode="wait">
                {openCards.settings && (
                    <motion.div
                        key="settings"
                        initial={{ opacity: 0, x: 0 }}
                        animate={{ opacity: 1, x: 0 }}
                        exit={{ opacity: 0, x: 0 }}
                        transition={{ duration: 0.3 }}
                    >
                        <SettingsCard />
                    </motion.div>
                )}
                {openCards.profile && (
                    <motion.div
                        key="profile"
                        initial={{ opacity: 0, x: 0 }}
                        animate={{ opacity: 1, x: 0 }}
                        exit={{ opacity: 0, x: 0 }}
                        transition={{ duration: 0.3 }}
                    >
                        <ProfileCard />
                    </motion.div>
                )}
            </AnimatePresence>
        </>
    );
}