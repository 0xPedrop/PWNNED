import Link from "next/link";
import styles from "./Header.module.css";
import CustomizedImage from "@/ui/components/CustomizedImage";

const Header = () => {
  return (
    <header className={styles.header}>
      <div style={{ display: "flex", flexDirection: "column", width: "100%" }}>
        <nav className={styles.navbar}>
          <div className={styles.logo}>PWNNED</div>
          <div className={styles.profile}>
            <Link href="/profile">
              <CustomizedImage
                src="/images/profile-photo.png"
                alt="Profile Photo"
                width={35}
                height={35}
              />
            </Link>
          </div>
        </nav>
      </div>
    </header>
  );
};

export default Header;
