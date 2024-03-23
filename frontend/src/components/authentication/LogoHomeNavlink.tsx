import { motion } from "framer-motion";
import React from "react";
import { SlGlobe } from "react-icons/sl";
import { NavLink } from "react-router-dom";

type Props = {};

function LogoHomeNavlink({}: Props) {
  return (
    <motion.div
      animate={{ scaleX: 1 }}
      whileHover={{
        scaleX: 1.01,
      }}
      transition={{ duration: 0.2 }}
    >
      <NavLink
        to={"/"}
        className="group flex justify-center items-center gap-2 cursor-pointer font-bold text-background-500 text-center w-full"
      >
        <SlGlobe className="text-3xl group-hover:rotate-6 transition-transform" />
        <p className="text-4xl">Travel Memories</p>
      </NavLink>
    </motion.div>
  );
}

export default LogoHomeNavlink;
