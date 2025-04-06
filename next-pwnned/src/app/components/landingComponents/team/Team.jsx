import styles from "./Team.module.css";
import Image from "next/image";

const Plans = () => {
  return (
    <div className={styles.bode}>
      <article className={styles.profile}>
        <div className={styles.profile_preview}>
          <Image src="/profile.jpg" alt="0xPedrop" width={500} height={500} />
        </div>
        <div className={styles.profile_right}>
          <div className={styles.profile_content}>
            <h2>Pedro Paulo</h2>
            <div className={styles.profile_skills}>
              <div className={styles.profile_skill_java}>
                <p>Java</p>
              </div>
              <div className={styles.profile_skill_cibersecurity}>
                <p>Cibersecurity</p>
              </div>
            </div>
            <p className={styles.profile_description}>
              Estudante de Análise e Desenvolvimento de Sistemas<br></br>
              <br></br>Bug Hunter
            </p>
          </div>
          <div className={styles.profile_footer}>
            <a
              href="https://github.com/0xPedrop"
              target="_blank"
              rel="noopener noreferrer"
              className={styles.btn}
            >
              Github
            </a>
          </div>
        </div>
      </article>
      <article className={styles.profile}>
        <div className={styles.profile_preview}>
          <Image src="/profile2.jpg" alt="0xJotave" width={500} height={500} />
        </div>
        <div className={styles.profile_right}>
          <div className={styles.profile_content}>
            <h2>João Victor</h2>
            <div className={styles.profile_skills}>
              <div className={styles.profile_skill_java}>
                <p>Java</p>
              </div>
              <div className={styles.profile_skill_cibersecurity}>
                <p>Cibersecurity</p>
              </div>
            </div>
            <p className={styles.profile_description}>
              Estudante de Análise e Desenvolvimento de Sistemas<br></br>
              <br></br>Bug Hunter
            </p>
          </div>
          <div className={styles.profile_footer}>
            <a
              href="https://github.com/0xJotave"
              target="_blank"
              rel="noopener noreferrer"
              className={styles.btn}
            >
              Github
            </a>
          </div>
        </div>
      </article>
    </div>
  );
};

export default Plans;
