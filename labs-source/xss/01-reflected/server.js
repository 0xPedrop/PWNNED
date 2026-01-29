const express = require('express');
const app = express();

app.set('view engine', 'ejs');
app.set('views', './views');
app.use(express.static('public'));

let isSolved = false;

app.get('/', (req, res) => {
    res.render('index', { query: req.query.q || '', solved: isSolved });
});

// Rota chamada pelo Hook de XSS
app.get('/solve', (req, res) => {
    console.log("[LAB] XSS Confirmado.");
    isSolved = true;
    res.status(200).send('OK');
});

// Mata o Container
app.post('/kill-container', (req, res) => {
    console.log("[LAB] Recebido comando de encerramento do usuário.");
    res.send("BYE");
    
    // Espera 500ms para dar tempo de enviar a resposta "BYE" e depois morre
    setTimeout(() => {
        process.exit(0); // Docker para o container imediatamente
    }, 500);
});

app.listen(80, () => {
    console.log("LABORATÓRIO ONLINE");
});