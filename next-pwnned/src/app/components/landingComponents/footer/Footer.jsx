import styles from "./Footer.module.css";
import Image from "next/image";
const Footer = () => {
  return (
    <div className={styles.bode}>
      <div className={styles.footer}>
        <div className={styles.container}>
          <article>
            <h4>Subscribe to our Newsletter</h4>
            <div className={styles.newsletterForm}>
              <input
                type="email"
                placeholder="Your email address"
                className={styles.emailInput}
              />
              <button className={styles.submitButton}>Sign up free</button>
            </div>
          </article>
          <section>
            <div className={styles.logo}>PWNNED</div>
            <div className={styles.socials}>
              <a
                className={styles.fa_instagram}
                href="https://instagram.com/pwnned"
              >
                <Image
                  src="/instagram-white.svg"
                  alt="Instagram"
                  width={30}
                  height={30}
                />
              </a>
              <a className={styles.fa_medium} href="https://medium.com/pwnned">
                <Image
                  src="/medium-white.svg"
                  alt="Medium"
                  width={40}
                  height={40}
                />
              </a>
            </div>
            <ul>
              <li>
                <h3>Resources</h3>
                <a>Usage</a>
              </li>
              <li>
                <h3>Resources</h3>
                <a>Usage</a>
              </li>
              <li>
                <h3>Resources</h3>
                <a>Usage</a>
              </li>
              <li>
                <h3>Resources</h3>
                <a>Usage</a>
              </li>
            </ul>
          </section>
        </div>
      </div>
    </div>
  );
};

export default Footer;
