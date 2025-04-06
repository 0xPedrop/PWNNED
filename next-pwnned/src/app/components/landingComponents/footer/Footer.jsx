import styles from "./Footer.module.css";
import Image from "next/image";
const Footer = () => {
  return (
    <div className={styles.bode}>
      <div className={styles.footer}>
        <div className={styles.container}>
          <article>
            <h4>Subscribe to our Newsletter</h4>
            <button>
              <p>Sign up free</p>
              <span> ⮕ </span>
            </button>
          </article>
          <section>
            <div className={styles.logo}>PWNNED</div>
            <div className={styles.socials}>
              <div className={styles.fa_brands}>
                <a className={styles.fa_instagram}></a>
                <a className={styles.fa_medium}></a>
              </div>
              <ul>
                <li>
                  <h3>Resources</h3>
                  <a>Usage</a>
                </li>
              </ul>
            </div>
          </section>
        </div>
      </div>
    </div>
  );
};

export default Footer;
