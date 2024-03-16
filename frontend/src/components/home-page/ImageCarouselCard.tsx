import React from "react";
import { BackgroundImageProps } from "./BackgroundImageProps";
import { motion } from "framer-motion";

function ImageCarouselCard({ img, location }: BackgroundImageProps) {
  return (
    <motion.div className="bg-white px-2 pt-4 pb-12 sm:px-4 sm:pt-8 sm:pb-24 shadow-lg rounded-lg font-backgroundImage flex flex-col items-center overflow-hidden gap-4 text-sm sm:text-2xl text-primary-900 w-full">
      <img
        src={img}
        alt=""
        className="object-cover w-full shadow-md aspect-[16/13]"
      />
      <p>{location}</p>
    </motion.div>
  );
}

export default ImageCarouselCard;
