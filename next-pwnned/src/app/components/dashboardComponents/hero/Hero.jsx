import styles from "./Hero.module.css";
import Card from "@/ui/components/Card";
import Image from "next/image";

export default function Hero() {
  return (
    <div className={styles.hero}>
      <div className={styles.generalInfo}>
        <h1 className={styles.title}>Learning Paths</h1>
        <span className={styles.description}>
          This is your playground! Have Fun!
        </span>
      </div>
      <div className={styles.pathsInfo}>
        <h1 className={styles.minorTitle}>Easy Paths</h1>
        <p className={styles.description}>
          These Paths are easy to understand because they don't require
          extensive prior knowledge,
          <br /> making them highly recommended for beginners.
        </p>
      </div>

      <div className={styles.paths}>
        <Card
          width="350px"
          height="240px"
          backgroundColor="var(--primary-600)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardEasy}
        >
          <div className={styles.pathContent}>
            <h1>Cross-Site Scripting (XSS)</h1>
            <p>
              Cross-site scripting (XSS) is a cyberattack that allows the
              injection of malicious code into trusted websites. The malicious
              code is executed in the victim's browser, allowing attackers to
              access sensitive information.
            </p>
            <hr style={{ color: "var(--primary-200)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="240px"
          backgroundColor="var(--primary-600)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardEasy}
        >
          <div className={styles.pathContent}>
            <h1>Information Disclosure</h1>
            <p>
              Information Disclosure occurs when sensitive information, intended
              to be private, is exposed to unauthorized users. This can include
              personal data, system details, or confidential business
              information.
            </p>
            <hr style={{ color: "var(--primary-200)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="240px"
          backgroundColor="var(--primary-600)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardEasy}
        >
          <div className={styles.pathContent}>
            <h1>ClickJacking</h1>
            <p>
              Clickjacking is a web security vulnerability where an attacker
              tricks users into clicking on a hidden element (like a button or
              link) on a webpage, often disguised within an innocuous-looking
              page.
            </p>
            <hr style={{ color: "var(--primary-200)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="240px"
          backgroundColor="var(--primary-600)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardEasy}
        >
          <div className={styles.pathContent}>
            <h1>Path Traversal</h1>
            <p>
              Path Traversal allows an attacker to access files and directories
              that are located outside the web server's root directory. By
              manipulating file paths in requests, an attacker might be able to
              read sensitive system files or execute arbitrary code.
            </p>
            <hr style={{ color: "var(--primary-200)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="240px"
          backgroundColor="var(--primary-600)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardEasy}
        >
          <div className={styles.pathContent}>
            <h1>Broken Link Hijacking</h1>
            <p>
              Broken Link Hijacking occurs when a website contains a link to an
              external resource that no longer exists (a "broken link"). An
              attacker can then register a similar or identical resource at that
              former location.
            </p>
            <hr style={{ color: "var(--primary-200)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="240px"
          backgroundColor="var(--primary-600)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardEasy}
        >
          <div className={styles.pathContent}>
            <h1>HTML Injection</h1>
            <p>
              HTML Injection arises when a web application allows users to
              control an input that is then included in its HTML output without
              proper sanitization. An attacker can inject arbitrary HTML code,
              which can then be rendered by other users' browsers.
            </p>
            <hr style={{ color: "var(--primary-200)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
      </div>

      <hr
        className={styles.separator}
        style={{ border: "1px solid var(--primary-500)" }}
      />

      <div className={styles.pathsInfo}>
        <h1 className={styles.minorTitle}>Medium Paths</h1>
        <p className={styles.description}>
          These Paths have a moderate complexity and require some understanding
          of web application concepts, <br />
          especially server-side processing.
        </p>
      </div>
      <div className={styles.paths}>
        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--primary-300)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardMedium}
        >
          <div className={styles.pathContent}>
            <h1>SQL Injection </h1>
            <p>
              SQL Injection arises when a web application improperly sanitizes
              user-supplied input that is then used in SQL queries. Attackers
              can inject malicious SQL code, potentially allowing them to read,
              modify, or delete data in the database, or even execute arbitrary
              commands on the database server.
            </p>
            <hr style={{ color: "var(--primary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--primary-300)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardMedium}
        >
          <div className={styles.pathContent}>
            <h1>Cross-Site Request Forgery (CSRF)</h1>
            <p>
              CSRF tricks logged-in users into performing unwanted actions on a
              web application. Attackers craft malicious requests (often in
              emails or websites) that the user's browser unknowingly sends to
              the vulnerable application as legitimate actions.
            </p>
            <hr style={{ color: "var(--primary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--primary-300)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardMedium}
        >
          <div className={styles.pathContent}>
            <h1>Server-Side Request Forgery (SSRF)</h1>
            <p>
              SSRF occurs when a web application can be manipulated to send
              requests to unintended locations, often internal resources or
              other external systems. An attacker can exploit this to bypass
              security controls, access sensitive information within the
              network, or interact with other services that are normally
              inaccessible from the outside.
            </p>
            <hr style={{ color: "var(--primary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--primary-300)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardMedium}
        >
          <div className={styles.pathContent}>
            <h1>Local File Inclusion (LFI)</h1>
            <p>
              This vulnerability allows an attacker to include files on the web
              server through the exploitation of insecurely handled file paths
              in user-provided input. By manipulating these paths, an attacker
              might be able to read sensitive configuration files, source code,
              or other local resources. In some cases, it can even lead to
              remote code execution.
            </p>
            <hr style={{ color: "var(--primary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--primary-300)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardMedium}
        >
          <div className={styles.pathContent}>
            <h1>XML External Entity (XXE) Injection</h1>
            <p>
              XXE vulnerabilities arise when an application parses XML input
              that contains references to external entities. Attackers can craft
              malicious XML payloads that instruct the XML parser to access
              local files on the server, interact with internal or external
              systems, or even trigger denial-of-service attacks.
            </p>
            <hr style={{ color: "var(--primary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--primary-300)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardMedium}
        >
          <div className={styles.pathContent}>
            <h1>Insecure Deserialization</h1>
            <p>
              Insecure Deserialization happens when an application converts
              untrusted data back into an object without proper checks.
              Attackers can manipulate this data to inject malicious code or
              objects, which can then be executed during the conversion,
              potentially leading to remote code execution or other security
              issues.
            </p>
            <hr style={{ color: "var(--primary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
      </div>

      <hr
        className={styles.separator}
        style={{ border: "1px solid var(--secondary-500)" }}
      />

      <div className={styles.pathsInfo}>
        <h1 className={styles.minorTitle}>Hard Paths</h1>
        <p className={styles.description}>
          These Paths are highly challenging, requiring deep expertise in
          complex web systems <br /> and advanced security concepts.
        </p>
      </div>

      <div className={styles.paths}>
        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--secondary-200)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardHard}
        >
          <div className={styles.pathContent}>
            <h1>Remote Code Execution (RCE)</h1>
            <p>
              RCE is a critical vulnerability that allows an attacker to execute
              arbitrary code on the server hosting the web application. This can
              be achieved through the exploitation of various flaws in the
              application, its dependencies, or the underlying operating system.
            </p>
            <hr style={{ color: "var(--secondary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>

        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--secondary-200)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardHard}
        >
          <div className={styles.pathContent}>
            <h1>Server-Side Template Injection (SSTI)</h1>
            <p>
              Server-Side Template Injection (SSTI) happens when attackers can
              inject malicious code into server-side template engines that
              dynamically generate web pages. Exploiting these points can allow
              them to execute arbitrary code on the server, read sensitive
              files, or perform other malicious actions with the application's
              privileges.
            </p>
            <hr style={{ color: "var(--secondary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>

        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--secondary-200)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardHard}
        >
          <div className={styles.pathContent}>
            <h1>Race Conditions</h1>
            <p>
              Race Conditions occur in concurrent applications when the result
              depends on the execution order of code parts. Attackers can
              manipulate timing to cause unexpected behavior or bypass security,
              often needing precise timing and knowledge of the application's
              internals.
            </p>
            <hr style={{ color: "var(--secondary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>

        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--secondary-200)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardHard}
        >
          <div className={styles.pathContent}>
            <h1>Web Cache Poisoning</h1>
            <p>
              Web Cache Poisoning lets attackers manipulate a web server's cache
              to serve malicious content to other users. By crafting specific
              HTTP requests, they can control what's stored in the cache,
              potentially causing XSS, information leaks, or other attacks on
              unsuspecting visitors.
            </p>
            <hr style={{ color: "var(--secondary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>

        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--secondary-200)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardHard}
        >
          <div className={styles.pathContent}>
            <h1>Cryptographic Vulnerabilities</h1>
            <p>
              Cryptographic vulnerabilities involve weaknesses in the
              implementation or usage of cryptographic algorithms and protocols.
              This can include using weak ciphers, predictable keys, incorrect
              padding schemes, or failing to properly handle cryptographic
              operations.
            </p>
            <hr style={{ color: "var(--secondary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>

        <Card
          width="350px"
          height="280px"
          backgroundColor="var(--secondary-200)"
          color="#000000"
          borderRadius="10px"
          className={styles.pathCardHard}
        >
          <div className={styles.pathContent}>
            <h1>Business Logic Vulnerabilities</h1>
            <p>
              Business Logic vulnerabilities are flaws in an application's
              design and core rules. Instead of direct code injection, they
              exploit intended behavior in unintended ways to gain access,
              manipulate data, or bypass security.
            </p>
            <hr style={{ color: "var(--secondary-100)" }} />
          </div>
          <div style={{ cursor: "pointer" }}>
            <a href="">Go To Path</a>
            <Image
              src="/next-white.png"
              alt="Next Icon"
              width={20}
              height={20}
              style={{
                objectFit: "contain",
                marginRight: "10px",
              }}
              className={styles.arrow}
            />
          </div>
        </Card>
      </div>
    </div>
  );
}
