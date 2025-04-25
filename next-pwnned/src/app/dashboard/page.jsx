import Header from "../components/dashboardComponents/header/Header";
import Hero from "../components/dashboardComponents/hero/Hero";
import { getServerSession } from "next-auth";
import { authOptions } from "../api/auth/[...nextauth]/route";
import { redirect } from "next/navigation";

export default async function Dashboard() {
  const session = await getServerSession(authOptions);

  if (!session) {
    redirect("/login");
  }

  return (
    <>
      <Header />
      <Hero />
    </>
  );
}
