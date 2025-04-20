import styles from "./Sidebar.module.css";
import Image from "next/image";
import Link from "next/link";

export default function Sidebar({ currentPath, progressSteps }) {
  return (
    <div className={styles.sidebar}>
      <h1 className={styles.title}>
        {currentPath ? currentPath.toUpperCase().replace("-", " ") : "Paths"}
      </h1>
      <h1 className={styles.progressTitle}>My Progress</h1>
      <ul className={styles.progressList}>
        {progressSteps.map((step, index) => (
          <li key={index} className={styles.progressItem}>
            <span className={styles.progressIndicator}>
              {step.status === "complete" ? (
                <div className={styles.completedIndicator}>
                  <Image
                    src="/check.png"
                    alt="Check Icon"
                    width={10}
                    height={10}
                    style={{
                      objectFit: "contain",
                      marginTop: "0.1rem",
                    }}
                  />
                </div>
              ) : step.status === "doing" ? (
                <div className={styles.doingIndicator}></div>
              ) : (
                <div className={styles.incompleteIndicator} />
              )}
            </span>
            <Link
              href={`/paths/${currentPath}/${step.slug}`}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              {step.label}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
