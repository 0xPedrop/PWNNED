import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080", // Porta do Spring Boot
  withCredentials: true, // enviar/receber Cookies (JSESSIONID)
});
