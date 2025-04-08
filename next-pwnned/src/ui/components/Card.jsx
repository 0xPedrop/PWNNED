import React from "react";

const Card = ({
  width,
  height,
  backgroundColor,
  color,
  shadow,
  border,
  className,
  children,
}) => {
  const cardStyle = {
    width: width,
    height: height,
    padding: "24px",
    backgroundColor: backgroundColor,
    color: color,
    borderRadius: "3px",
    border: border,
    boxShadow: shadow,
  };

  return (
    <div style={cardStyle} className={className}>
      {children}
    </div>
  );
};

export default Card;
