import { toast } from "react-toastify";

export const signup = async (userData) => {
  const response = await fetch("http://localhost:8080/auth/signup", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userData),
  });

  console.log("Resposta completa:", response);
  const responseData = await response.text();
  console.log("Corpo da resposta:", responseData);

  if (!response.ok) {
    let errorMessage = "Erro ao cadastrar usuário";
    try {
      const errorData = JSON.parse(responseData);
      errorMessage = errorData.error || errorMessage;
    } catch (e) {
      console.error("Erro ao parsear JSON de erro:", e);
      errorMessage = responseData || errorMessage;
    }

    if (errorMessage.includes("email")) {
      toast.error("O email já está em uso.");
      throw new Error("O email já está em uso.");
    } else if (errorMessage.includes("username")) {
      toast.error("O username já está em uso.");
      throw new Error("O username já está em uso.");
    } else {
      toast.error(errorMessage);
      throw new Error(errorMessage);
    }
  }

  try {
    const result = JSON.parse(responseData);
    return { message: result.message };
  } catch (e) {
    console.error("Erro ao parsear JSON de sucesso:", e);
    return { message: "Erro desconhecido" };
  }
};
