import axios from "axios";

export const api = axios.create({

  baseURL: "/api/v1", 
  
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, 
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    return Promise.reject(error);
  }
);