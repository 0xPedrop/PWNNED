"use client";
import styles from "./LoginCard.module.css";
import { useState } from "react";

export default function LoginCard() {
  const [input, setInput] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [invalidCommand, setInvalidCommand] = useState(false);
  const [username, setUsername] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const handleCommand = (e) => {
    e.preventDefault();

    if (input.trim().toLowerCase() === "ls") {
      setShowForm(true);
      setInvalidCommand(false);
    } else {
      setInvalidCommand(true);
    }

    setInput(""); // limpa o campo de input depois de digitar
  };

  const validateUsername = (value) => {
    setUsername(value);
    const usernameRegex = /^[a-zA-Z0-9]{4,10}$/;
    setUsernameError(
      usernameRegex.test(value)
        ? ""
        : "Usuário deve ter 4-10 letras/números, sem espaços ou símbolos"
    );
  };

  const validatePassword = (value) => {
    setPassword(value);
    const passwordRegex = /^[^\s]{8,20}$/;
    const hasLetter = /[a-zA-Z]/.test(value);
    const hasNumber = /[0-9]/.test(value);
    const hasSpecial = /[^a-zA-Z0-9]/.test(value);

    if (!passwordRegex.test(value)) {
      setPasswordError("Senha deve ter 8-20 caracteres, sem espaços");
    } else if (!(hasLetter && hasNumber && hasSpecial)) {
      setPasswordError(
        "Senha precisa ter letras, números e caracteres especiais"
      );
    } else {
      setPasswordError("");
    }
  };

  return (
    <div className={styles.card}>
      <h2 className={styles.title}>Login</h2>

      <div className={styles.terminal}>
        <div className={styles.prompt}>
          <span className={styles.user}>┌──(root㉿root)-[~]</span>
          <div className={styles.hashAndInput}>
            <span className={styles.hash}>└─#</span>
            <form onSubmit={handleCommand} className={styles.commandForm}>
              <input
                type="text"
                value={input}
                onChange={(e) => setInput(e.target.value)}
                className={styles.commandInput}
                autoFocus
                autoComplete="off"
              />
            </form>
          </div>
        </div>

        {invalidCommand && (
          <div className={styles.error}>Comando inválido. Tente 'ls'</div>
        )}

        {showForm && (
          <form className={styles.loginForm}>
            <div className={styles.floatingGroup}>
              <input
                type="text"
                id="username"
                name="username"
                placeholder=" "
                required
                className={`${styles.floatingInput} ${
                  !username ? styles.empty : ""
                } ${usernameError ? styles.error : ""}`}
                value={username}
                onChange={(e) => validateUsername(e.target.value)}
              />
              <label htmlFor="username" className={styles.floatingLabel}>
                Usuário
              </label>
              {usernameError && (
                <div className={styles.error}>{usernameError}</div>
              )}
            </div>

            <div className={styles.floatingGroup}>
              <input
                type="password"
                id="password"
                name="password"
                placeholder=" "
                required
                className={`${styles.floatingInput} ${
                  !password ? styles.empty : ""
                } ${passwordError ? styles.error : ""}`}
                value={password}
                onChange={(e) => validatePassword(e.target.value)}
              />
              <label htmlFor="password" className={styles.floatingLabel}>
                Senha
              </label>
            </div>

            <a
              href="#"
              target="_blank"
              rel="noopener noreferrer"
              className={styles.forgotPassword}
            >
              Esqueci minha senha
            </a>

            <button type="submit" className={styles.button}>
              Entrar
            </button>
          </form>
        )}
      </div>
    </div>
  );
}
