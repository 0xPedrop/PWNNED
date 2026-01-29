# --- Estágio 1: Build do React (Node.js) ---
FROM node:20-alpine AS build
WORKDIR /app

# Copia os arquivos de dependência
COPY package*.json ./
RUN npm install

# Copia o resto do projeto e faz o build
COPY . .
RUN npm run build

# --- Estágio 2: Servidor Web (Nginx) ---
FROM nginx:alpine

# Copia os arquivos gerados no estágio anterior (pasta dist) para o Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Copia a sua configuração personalizada do Nginx (que criamos antes)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]