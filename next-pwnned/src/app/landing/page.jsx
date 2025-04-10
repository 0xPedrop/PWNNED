import Plans from "../components/landingComponents/plans/Plans";
import About from "../components/landingComponents/about/About";
import Header from "../components/landingComponents/header/Header";
import Hero from "../components/landingComponents/hero/Hero";
import Team from "../components/landingComponents/team/Team";
import Footer from "../components/landingComponents/footer/Footer";

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
      <Plans />
      <Team />
      <Footer />
    </>
  );
};

export default LandingPage;
