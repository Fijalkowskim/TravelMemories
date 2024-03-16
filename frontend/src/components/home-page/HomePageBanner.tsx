import { motion } from "framer-motion";
import React from "react";
import { NavLink } from "react-router-dom";

function HomePageBanner() {
  const transitionDelay = 0.7;

  const buttonAnimationVariants = {
    initial: { scale: 0 },
    animate: { scale: 1 },
    whileHover: { scale: 1.02 },
    whileTap: { scale: 1.01 },
  };
  const heroAnimationVariants = {
    initial: { opacity: 0, y: 5, scaleX: 1 },
    animate: { opacity: 1, y: 0, scaleX: 1 },
  };
  return (
    <div className="flex flex-col items-center justify-center gap-2 sm:gap-4 cursor-default z-10 w-fit ">
      <motion.h1
        className="hidden sm:block tracking-tighter text-center origin-center text-5xl sm:text-7xl xl:text-7xl -mb-2"
        variants={heroAnimationVariants}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 1 * transitionDelay, duration: 0.4 }}
        key="home-header"
      >
        Capture Your
      </motion.h1>
      <motion.h1
        className=" text-center font-bold origin-center text-4xl sm:text-7xl xl:text-8xl"
        variants={heroAnimationVariants}
        initial="initial"
        animate="animate"
        transition={{ delay: 2.3 * transitionDelay, duration: 0.7 }}
        key="home-header"
      >
        Travel Memories
      </motion.h1>
      <motion.p
        className="text-lg sm:text-2xl tracking-tighter sm:tracking-normal whitespace-pre-wrap text-center mx-auto text-primary-950/80 cursor-default sm:max-w-[70%] max-w-xs"
        variants={heroAnimationVariants}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 3.5 * transitionDelay, duration: 0.7 }}
        key="home-p"
      >
        Collect, share and remember your adventures in a space that celebrates
        the essence of exploration.
      </motion.p>
      <motion.div
        className="flex-col gap-2 flex w-fit sm:gap-10 sm:flex-row"
        variants={heroAnimationVariants}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 3.5 * transitionDelay, duration: 0.7 }}
      >
        <NavLink to={"/login"}>
          <motion.button
            className="bg-action-400 w-40 sm:w-52 hover:bg-action-500 sm:px-10 py-4 sm:text-xl rounded-2xl tracking-tight font-bold text-background-50  shadow-md transition-colors"
            variants={buttonAnimationVariants}
            initial="initial"
            animate="animate"
            whileHover="whileHover"
            whileTap="whileTap"
          >
            Get Started
          </motion.button>
        </NavLink>
        <NavLink to={"/public-memories"}>
          <motion.button
            className=" bg-background-50 w-40 sm:w-52 hover:bg-background-100 sm:px-10 py-4 sm:text-xl rounded-2xl tracking-tight font-bold text-primary-950 shadow-md transition-colors"
            variants={buttonAnimationVariants}
            initial="initial"
            animate="animate"
            whileHover="whileHover"
            whileTap="whileTap"
          >
            Discover
          </motion.button>
        </NavLink>
      </motion.div>
    </div>
  );
}

export default HomePageBanner;
