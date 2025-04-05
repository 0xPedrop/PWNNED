// About.jsx
import styles from "./About.module.css";

const About = () => {
  return (
    <section className={styles.about}>
      <div className={styles.content}>
        <h1>
          LEARN FROM REAL SCENARIOS
          <br />
          AND BE PREPARED FOR ANYTHING<span className={styles.cursor}>_</span>
        </h1>
        <p>
          Este é um texto de teste para verificar se o componente About está
          sendo renderizado corretamente. Adicione mais conteúdo aqui para
          aumentar a altura da página e gerar scroll.
        </p>
      </div>
    </section>
  );
};

export default About;
