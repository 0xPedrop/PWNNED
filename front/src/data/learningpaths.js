export const learningPathsData = [
  {
    id: "270263353404952576",
    title: "SQL Injection Mastery",
    description: "Learn to identify and exploit SQL injection vulnerabilities.",
    difficulty: "Medium",
    xp: 500,
    duration: "4h",
    students: 12500,
    modules: 3,
    labUrl: "/lab/sql-injection-1",
    modulesContent: [
      {
        title: "Understanding SQL Injection",
        content: `
## O que é SQL Injection?
SQL injection é Uma técnica de injeção de código que explora vulnerabilidades de segurança no software de um aplicativo.
### O Básico
Antes de se aprofundar na exploração, é crucial entender como funcionam os bancos de dados SQL.
\`\`\`sql
SELECT * FROM users WHERE username = 'admin';
\`\`\`
        `
      },
      {
        title: "In-Band SQLi (Clássico)",
        content: `
## In-Band SQLi
O atacante utiliza o mesmo canal de comunicação para lançar ataques e coletar resultados.

### Union Based
Utilizando o operador UNION para combinar resultados.
\`\`\`sql
' UNION SELECT username, password FROM users--
\`\`\`
        `
      },
      {
        title: "Blind SQL Injection",
        content: `
## Blind SQLi
O atacante envia payloads e observa o comportamento do aplicativo em vez de visualizar os dados diretamente.
`
      }
    ]
  },
  {
    id: "270263701620264960",
    title: "Cross-Site Scripting (XSS)",
    description: "Domine a arte dos ataques XSS. Reflected, Stored, and DOM-based.",
    difficulty: "Easy",
    xp: 750,
    duration: "6h",
    students: 8900,
    modules: 3,
    labUrl: "/lab/xss-1",
    modulesContent: [
      {
        title: "Reflected XSS",
        content: `
## Reflected XSS
O script malicioso provém da solicitação HTTP atual.

### Example
\`\`\`html
https://example.com/search?q=<script>alert('XSS')</script>
\`\`\`
Este é o tipo mais comum de XSS.
        `
      },
      {
        title: "Stored XSS",
        content: `
## Stored XSS (Persistente)
O script malicioso é armazenado no servidor alvo (banco de dados, postagem em fórum, campo de comentários) e posteriormente enviado às vítimas.

### Por que é perigoso
Diferentemente do XSS refletido, a vítima não precisa clicar em um link específico. Basta visitar a página para que o payload seja acionado.
        `
      },
      {
        title: "DOM-based XSS",
        content: `
## DOM-based XSS
A vulnerabilidade existe no código do lado do cliente, e não no código do lado do servidor.

\`\`\`javascript
// Código vulnerável
document.getElementById("output").innerHTML = location.hash.slice(1);
\`\`\`
        `
      }
    ]
  },
  // ... outros cursos ...
];