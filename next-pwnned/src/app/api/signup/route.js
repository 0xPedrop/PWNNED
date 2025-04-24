export async function POST(req) {
  try {
    const { email, username, password } = await req.json();

    const springResponse = await fetch("http://localhost:8080/api/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, username, password }),
    });

    const responseData = await springResponse.json();

    return new Response(JSON.stringify(responseData), {
      status: springResponse.status,
    });
  } catch (err) {
    console.error("Erro no proxy da API:", err);
    return new Response(JSON.stringify({ error: "Erro interno no servidor" }), {
      status: 500,
    });
  }
}
