import styles from "./Header.module.css";

const Header = () => {
  return (
    <header className={styles.header}>
      <div style={{ display: "flex", flexDirection: "column", width: "100%" }}>
        <nav className={styles.navbar}>
          <div className={styles.logo}>PWNNED</div>
        </nav>
      </div>
    </header>
  );
};

export default Header;
