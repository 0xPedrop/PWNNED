import Link from "next/link";
import styles from "./Header.module.css";

const Header = () => {
  return (
    <header className={styles.header}>
      <nav className={styles.navbar}>
        <div className={styles.logo}>PWNNED</div>
        <div className={styles.authLinks}>
          <Link href="/login" className={styles.link}>
            Login
          </Link>
          <Link href="/signup" className={styles.signupButton}>
            Signup
          </Link>
        </div>
      </nav>
    </header>
  );
};

export default Header;
