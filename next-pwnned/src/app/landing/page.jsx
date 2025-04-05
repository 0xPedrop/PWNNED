// page.jsx
import About from "../components/landingComponents/about/About";
import Header from "../components/landingComponents/header/Header";
import Hero from "../components/landingComponents/hero/Hero";

const LandingPage = () => {
  return (
    <>
      <Header />
      <main
        style={{ marginTop: "60px", textAlign: "center", minHeight: "100vh" }}
      >
        <Hero />
      </main>
      <About />
    </>
  );
};

export default LandingPage;
