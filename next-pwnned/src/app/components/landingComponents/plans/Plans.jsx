import styles from "./Plans.module.css";

const Plans = () => {
  return (
    <section
      className={`${styles.bode} selection:bg-blue-500 selection:text-white`}
    >
      <div className={styles.pricing_container}>
        <article className={styles.pricing_card}>
          <h3>Free</h3>
          <div>Free features</div>
          <div className={styles.pricing_card_price_original}>
            <s>R$0,00</s>
          </div>
          <div className={styles.pricing_card_price}>R$0,00</div>
          <ul>
            <li>Access to free content and resources only</li>
            <li>Email support with no estimated response time.</li>
          </ul>
          <a className={styles.enroll} href="#">
            Choose Free
          </a>
        </article>
        <article className={styles.pricing_card}>
          <h3>Premium</h3>
          <div>Premium features</div>
          <div className={styles.pricing_card_price_original}>
            <s>R$25,99</s>
          </div>
          <div className={styles.pricing_card_price}>R$12,99</div>
          <ul>
            <li>Access to all content and resources</li>
            <li>Email support with a 48-hour response time.</li>
            <li>Monthly newsletter with updates and tips.</li>
          </ul>
          <a className={styles.enroll} href="#">
            Choose Premium
          </a>
        </article>
      </div>
    </section>
  );
};

export default Plans;
