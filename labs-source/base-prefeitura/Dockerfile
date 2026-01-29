FROM node:18-alpine

# Cria diretório de trabalho
WORKDIR /app

# Instala dependências comuns (Express e EJS são usados por quase todos os labs)
COPY package.json ./
RUN npm install

# Copia os arquivos de "Assets" (CSS, Imagens) e "Views" (Header, Footer)
COPY public ./public
COPY views ./views

# Não expomos porta nem rodamos CMD aqui, pois esta é apenas uma imagem base.
# Os labs filhos (reflected, stored) é que vão definir o server.js e o CMD.