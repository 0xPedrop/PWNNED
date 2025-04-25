import styles from "./profile.module.css";
import OptionsCard from "../components/profileComponents/optionsCard/OptionsCard";
import Header from "../components/profileComponents/header/Header";
import { getServerSession } from "next-auth";
import { authOptions } from "../api/auth/[...nextauth]/route";
import { redirect } from "next/navigation";

export default async function ProfilePage() {
  const session = await getServerSession(authOptions);

  if (!session) {
    redirect("/login");
  }
  return (
    <div className={styles.background}>
      <Header />
      <div className={styles.cards}>
        <OptionsCard />
      </div>
    </div>
  );
}
