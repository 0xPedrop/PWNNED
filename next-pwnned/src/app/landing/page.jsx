import Header from "../components/landingComponents/header/Header";
import Hero from "../components/landingComponents/hero/Hero";

const LandingPage = () => {
  return (
    <>
      <Header />
      <main style={{ marginTop: "60px", textAlign: "center" }}>
        <Hero />
      </main>
    </>
  );
};

export default LandingPage;
