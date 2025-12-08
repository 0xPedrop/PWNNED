# 🛡️ Pwnned - Cybersecurity Education Platform

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green?style=for-the-badge&logo=spring)
![Next.js](https://img.shields.io/badge/Next.js-13-black?style=for-the-badge&logo=next.js)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Production-blue?style=for-the-badge&logo=kubernetes)
![Docker](https://img.shields.io/badge/Docker-Container-blue?style=for-the-badge&logo=docker)

## 🚀 About the Project

**Pwnned** is an educational platform designed to democratize access to cybersecurity knowledge. It provides a gamified environment where users can learn hacking techniques, participate in CTFs (Capture The Flag), and track their progress.

More than just a web app, Pwnned was built to simulate a **scalable, production-ready environment**, applying modern software engineering practices.

> **Why I built this:** As a Bug Hunter and Software Engineering student, I wanted to bridge the gap between offensive security theory and practical application, while mastering complex infrastructure orchestration.

---

## 🏗️ Architecture & Tech Stack

This project goes beyond a simple CRUD. It is designed with scalability and security in mind.

### Backend
* **Java 17 & Spring Boot:** Core API handling business logic, user management, and progression tracking.
* **Spring Security:** Robust authentication and authorization implementation (JWT).
* **PostgreSQL:** Relational database for persistent data.

### Frontend
* **Next.js (React):** Server-side rendering for performance and SEO, providing a modern and responsive UI.
* **Tailwind CSS:** For rapid and consistent UI styling.

### Infrastructure & DevOps
* **Docker:** Containerization of all services to ensure consistency across environments.
* **Kubernetes (K8s):** Orchestration layer to manage container lifecycle, scaling, and networking.
* **CI/CD Concepts:** Automated build and deployment pipelines (in progress).

---

## 🤖 Roadmap: Gen AI Integration (Next Steps)

This project is constantly evolving. The next major milestone is integrating **Generative AI** to enhance the learning experience:

* **AI Tutor:** An LLM-based assistant to provide contextual hints (without giving away the answer) when students get stuck on a challenge.
* **Dynamic CTF Generation:** Using AI to generate unique vulnerability scenarios and flags, ensuring content never becomes repetitive.

---

## 🏆 About the Author

**Pedro Paulo Delgado Alves**
* 🎓 Systems Analysis and Development Student at IFPB.
* 🐞 **Bug Hunter & Pentester:** Passionate about breaking things to build them stronger.
* 🏅 **Recognition:** First Brazilian to be inducted into the **University of Nebraska Security Hall of Fame** for reporting critical vulnerabilities.

---

## 🛠️ How to Run

*(Instructions on how to clone and run with Docker Compose or K8s apply here)*

```bash
# Clone the repository
git clone [https://github.com/YOUR_USER/pwnned.git](https://github.com/YOUR_USER/pwnned.git)

# Run with Docker Compose
docker-compose up --build
