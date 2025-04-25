import Header from "@/app/components/pathsComponents/header/Header";
import Sidebar from "@/app/components/pathsComponents/sidebar/Sidebar";
import styles from "./PathsLayout.module.css";
import Button from "@mui/material/Button";
import Link from "next/link"; // Import Link

const PathsLayout = ({
  children,
  currentPath,
  progressSteps,
  nextTopicUrl,
}) => {
  return (
    <div className={styles.pathPage}>
      <Header />
      <div className={styles.sidebar}>
        <Sidebar currentPath={currentPath} progressSteps={progressSteps} />
      </div>
      <div className={styles.content}>
        {children}
        {nextTopicUrl ? (
          <Link href={nextTopicUrl} style={{ textDecoration: "none" }}>
            <Button variant="contained" disableElevation sx={buttonStyles}>
              Continue
            </Button>
          </Link>
        ) : (
          <Link href="/dashboard" style={{ textDecoration: "none" }}>
            <Button variant="contained" disableElevation sx={buttonStyles}>
              Finish Path
            </Button>
          </Link>
        )}
      </div>
    </div>
  );
};

const buttonStyles = {
  backgroundColor: "var(--primary-200)",
  "&:hover": {
    backgroundColor: "var(--primary-300)",
    boxShadow: "0px 5px 15px var(--background-100)",
  },
  marginTop: "15px",
  alignSelf: "flex-end",
  padding: "4px",
  width: "10rem",
  height: "3rem",
  position: "fixed",
  bottom: "2rem",
  right: "2rem",
  textTransform: "uppercase",
  borderRadius: "100px",
};

export default PathsLayout;
