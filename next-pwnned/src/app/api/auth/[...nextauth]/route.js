import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

const handler = NextAuth({
  providers: [
    CredentialsProvider({
      name: "credentials",
      async authorize(credentials) {
        const { username, password } = credentials;

        const res = await fetch("http://localhost:8080/auth/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ username, password }),
        });

        const user = await res.json();

        if (!res.ok) {
          throw new Error(user.message || "Credenciais inválidas");
        }

        return {
          id: user.id,
          name: user.username,
          email: user.email || null,
        };
      },
    }),
  ],
  pages: {
    error: "/login",
  },
});

export const GET = handler;
export const POST = handler;
