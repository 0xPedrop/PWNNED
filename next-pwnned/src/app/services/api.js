const API_BASE_URL = "http://localhost:8080"; // ajuste se necessário

export const signup = async (userData) => {
  const response = await fetch("http://localhost:8080/auth/signup", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userData),
  });

  // Isso aqui verifica se o status está entre 200 e 299
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Erro ao cadastrar usuário");
  }

  // Aqui usa .text() pois o backend tá retornando uma string simples
  const message = await response.text();
  return { message }; // retorna como objeto pra usar depois se quiser
};
