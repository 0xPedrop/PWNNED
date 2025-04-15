import LoginCard from "../components/loginComponents/logincard/LoginCard";
import styles from "./Login.module.css";

export default function LoginPage() {
  return (
    <div className={styles.loginPage}>
      <LoginCard />
    </div>
  );
}
