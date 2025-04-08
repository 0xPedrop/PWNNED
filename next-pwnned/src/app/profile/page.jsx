import styles from "./profile.module.css";
import OptionsCard from "../components/profileComponents/optionsCard/OptionsCard";

export default function ProfilePage() {
  return (
    <div className={styles.background}>
      <div className={styles.cards}>
        <OptionsCard />
      </div>
    </div>
  );
}
