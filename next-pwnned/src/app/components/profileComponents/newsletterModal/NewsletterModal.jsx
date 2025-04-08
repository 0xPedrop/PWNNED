"use client";
import { createPortal } from "react-dom";
import { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import Button from "@mui/material/Button";
import styles from "./NewsletterModal.module.css";

export default function NewsletterModal({ isOpen, onClose }) {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return null;

  return createPortal(
    <AnimatePresence>
      {isOpen && (
        <motion.div
          key="modal"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{ duration: 0.2 }}
          className={styles.backdrop}
        >
          <motion.div
            initial={{ opacity: 0, y: 0 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: 0 }}
            transition={{ duration: 0.2 }}
            className={styles.dialog}
          >
            <h2>Newsletter Subscription</h2>
            <p>Enter your email to get the latest news</p>
            <div className={styles.formGroupField}>
              <input
                type="email"
                className={styles.formField}
                placeholder="Email"
                name="email"
                id="email"
                required
              />
              <label htmlFor="email" className={styles.formLabel}>
                Email
              </label>
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
                padding: "4px",
                width: "130px",
                height: "35px",
                position: "absolute",
                bottom: "20px",
                right: "20px",
              }}
            >
              Save Changes
            </Button>
            <button
              onClick={onClose}
              aria-label="close"
              className={styles.closeModel}
            >
              ❌
            </button>
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>,
    document.body
  );
}
