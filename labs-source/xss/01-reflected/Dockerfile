FROM pwnned/lab-base-prefeitura:latest

WORKDIR /app

# Copia o server.js
COPY server.js .

# IMPORTANTE: Copia a view específica deste lab (index.ejs)
# O comando abaixo copia a pasta 'views' local para dentro de '/app/views'
COPY views ./views

EXPOSE 80
CMD ["node", "server.js"]