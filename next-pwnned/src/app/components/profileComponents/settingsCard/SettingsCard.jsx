"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { ChevronDown } from "lucide-react";
import Card from "@/ui/components/Card";
import styles from "./SettingsCard.module.css";
import Image from "next/image";
import NewsletterModal from "../newsletterModal/NewsletterModal";

const options = ["English", "Português(BR)"];

export default function SettingsCardContent() {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState(options[0]);
  const [modalOpen, setModalOpen] = useState(false);

  const toggleDropdown = () => setIsOpen((prev) => !prev);
  const handleSelect = (option) => {
    setSelected(option);
    setIsOpen(false);
  };

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  return (
    <div className={styles.cards}>
      <Card
        width="42rem"
        height="10rem"
        backgroundColor="var(--background-75)"
        shadow="0px 5px 15px var(--background-100)"
        color="var(--text-900)"
        borderRadius="5px"
        className={styles.plainSettings}
      >
        <h1 className={styles.title}>Plain Settings</h1>
        <div className={styles.plain}>
          <h1>
            <b>Actual Plain</b>
          </h1>
          <h1 className={styles.freeText}>
            <i>Free</i>
          </h1>
        </div>
        <a href="/plains" className={styles.upgradeButton}>
          <svg>
            <rect x="0" y="0" fill="none" width="100%" height="100%" />
          </svg>
          UPGRADE TO PREMIUM
        </a>
      </Card>
      <div className={styles.downCards}>
        <Card
          width="18.8rem"
          height="8rem"
          backgroundColor="var(--background-75)"
          shadow="0px 5px 15px var(--background-100)"
          color="var(--text-900)"
          borderRadius="5px"
        >
          <h1 className={styles.title}>
            <b>Language Settings</b>
          </h1>
          <div className={styles.relativeDropdown}>
            <button onClick={toggleDropdown} className={styles.dropdownButton}>
              <span>{selected}</span>
              <ChevronDown
                className={`${styles.chevron} ${
                  isOpen ? styles.rotate180 : ""
                }`}
              />
            </button>

            <AnimatePresence>
              {isOpen && (
                <motion.ul
                  initial={{ opacity: 0, y: -10 }}
                  animate={{ opacity: 1, y: 0 }}
                  exit={{ opacity: 0, y: -10 }}
                  className={styles.dropdownList}
                >
                  {options.map((option, index) => (
                    <li
                      key={index}
                      onClick={() => handleSelect(option)}
                      className={styles.dropdownItem}
                    >
                      {option}
                    </li>
                  ))}
                </motion.ul>
              )}
            </AnimatePresence>
          </div>
        </Card>
        <Card
          width="18.8rem"
          height="8rem"
          backgroundColor="var(--background-75)"
          shadow="0px 5px 15px var(--background-100)"
          color="var(--text-900)"
          borderRadius="5px"
        >
          <h1 className={styles.title}>
            <b>Subscriptions Settings</b>
          </h1>
          <div className={styles.iconNameContainer}>
            <Image
              src="/newsletter.png"
              alt="Newsletter Icon"
              width={20}
              height={20}
              style={{ objectFit: "contain" }}
            />
            <h1 className={styles.description}>
              <b>Newsletter</b>
            </h1>
            <button onClick={openModal} className={styles.subscriptionButton}>
              Subscribe
            </button>
          </div>
        </Card>
      </div>
      <NewsletterModal isOpen={modalOpen} onClose={closeModal} />
    </div>
  );
}
