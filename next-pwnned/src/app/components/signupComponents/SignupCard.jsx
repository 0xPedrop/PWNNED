"use client";
import { signup } from "@/app/services/api";
import styles from "./SignupCard.module.css";
import { useState } from "react";
import { useRouter } from "next/navigation"; // Certificando-se de usar o useRouter do Next.js

// Toast Notification library (assumindo que você está usando alguma)
import { toast } from "react-toastify"; // Verifique se você tem o react-toastify instalado

export default function SignupCard() {
  const [input, setInput] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [invalidCommand, setInvalidCommand] = useState(false);

  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [emailError, setEmailError] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter(); // Agora usando o useRouter corretamente

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Se houver erros, não faz o cadastro
    if (emailError || usernameError || passwordError) return;

    setIsLoading(true);

    try {
      const result = await signup({ email, username, password });

      // Exibir notificação de sucesso
      toast.success(result.message || "Cadastro realizado com sucesso!");

      // Após a notificação, redirecionar para a página de login
      setTimeout(() => {
        router.push("/login");
      }, 2000); // Atraso para a notificação ser vista antes do redirecionamento
    } catch (error) {
      // Caso ocorra um erro, exibe a mensagem de erro
      toast.error("Erro ao cadastrar");
    } finally {
      setIsLoading(false);
    }
  };

  const handleCommand = (e) => {
    e.preventDefault();

    if (input.trim().toLowerCase() === "ls") {
      setShowForm(true);
      setInvalidCommand(false);
    } else {
      setInvalidCommand(true);
    }

    setInput("");
  };

  const validateEmail = (value) => {
    setEmail(value);
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    setEmailError(emailRegex.test(value) ? "" : "Email inválido");
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
      <h2 className={styles.title}>Signup</h2>

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
          <form className={styles.signupForm} onSubmit={handleSubmit}>
            <div className={styles.floatingGroup}>
              <input
                type="text"
                id="email"
                name="email"
                placeholder=" "
                required
                className={`${styles.floatingInput} ${
                  !email ? styles.empty : ""
                } ${emailError ? styles.error : ""}`}
                value={email}
                onChange={(e) => validateEmail(e.target.value)}
              />
              <label htmlFor="email" className={styles.floatingLabel}>
                Email
              </label>
              {emailError && <div className={styles.error}>{emailError}</div>}
            </div>

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
              {passwordError && (
                <div className={styles.error}>{passwordError}</div>
              )}
            </div>

            <button
              type="submit"
              className={styles.button}
              disabled={
                !!(emailError || usernameError || passwordError) || isLoading
              }
            >
              {isLoading ? "Cadastrando..." : "Cadastrar-se"}
            </button>
          </form>
        )}
      </div>
    </div>
  );
}
