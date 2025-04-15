import Image from "next/image";
import React from "react";

const CustomizedImage = ({ src, alt, width, height, margin }) => {
  const customizedImageStyle = {
    width: width,
    height: height,
    borderRadius: "100px",
    overflow: "hidden",
    border: "2px solid white",
    margin: margin,
  };

  return (
    <div style={customizedImageStyle}>
      <Image src={src} alt={alt} width={width} height={height} />
    </div>
  );
};

export default CustomizedImage;
