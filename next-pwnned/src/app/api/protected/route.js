import { getToken } from "next-auth/jwt";

export async function GET(req) {
  const token = await getToken({ req, secret: process.env.NEXTAUTH_SECRET });

  if (!token) {
    return new Response("Unauthorized", { status: 401 });
  }

  console.log("🎟️ Token JWT:", token);
  return new Response(JSON.stringify({ userId: token.id }), { status: 200 });
}
