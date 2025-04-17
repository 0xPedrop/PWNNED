import styles from "./profile.module.css";
import OptionsCard from "../components/profileComponents/optionsCard/OptionsCard";
import Header from "../components/profileComponents/header/Header";

export default function ProfilePage() {
  return (
    <div className={styles.background}>
      <Header />
      <div className={styles.cards}>
        <OptionsCard />
      </div>
    </div>
  );
}
