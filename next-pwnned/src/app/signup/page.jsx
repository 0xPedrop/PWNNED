import SignupCard from "../components/signupComponents/SignupCard";
import styles from "./Signup.module.css";

export default function SignupPage() {
  return (
    <div className={styles.signupPage}>
      <SignupCard />
    </div>
  );
}
