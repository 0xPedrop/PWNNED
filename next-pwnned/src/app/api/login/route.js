export async function POST(req) {
  try {
    const { email, password } = await req.json();

    const springResponse = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    const responseData = await springResponse.json();

    if (!springResponse.ok) {
      return new Response(JSON.stringify(responseData), {
        status: springResponse.status,
      });
    }
    return new Response(JSON.stringify(responseData), {
      status: 200,
    });
  } catch (err) {
    console.error("Erro no proxy da API (login):", err);
    return new Response(JSON.stringify({ error: "Erro interno no servidor" }), {
      status: 500,
    });
  }
}
