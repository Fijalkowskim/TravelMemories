import { motion } from "framer-motion";
import React from "react";
import { NavLink } from "react-router-dom";
import { useSettingsContext } from "../../context/SettingsContext";

function HomePageBanner() {
  const { setFirstLoading, firstLoading } = useSettingsContext();
  const transitionDelay = 0.5;
  const transitionDuration = 0.7;
  const animationVariants = {
    initial: { opacity: 0 },
    animate: { opacity: 1 },
  };

  return (
    <div className="flex flex-col items-center justify-center gap-2 sm:gap-4 cursor-default z-10 w-fit ">
      <motion.h1
        className="hidden sm:block tracking-tighter text-center origin-center text-5xl sm:text-7xl xl:text-7xl -mb-2"
        variants={animationVariants}
        initial={firstLoading ? "initial" : ""}
        animate={firstLoading ? "animate" : ""}
        transition={{
          delay: 1 * transitionDelay,
          duration: transitionDuration,
        }}
      >
        Capture Your
      </motion.h1>
      <motion.h1
        className=" text-center font-bold origin-center text-4xl sm:text-7xl xl:text-8xl"
        variants={animationVariants}
        initial={firstLoading ? "initial" : ""}
        animate={firstLoading ? "animate" : ""}
        transition={{
          delay: 3 * transitionDelay,
          duration: transitionDuration * 2,
        }}
        key="home-header"
      >
        Travel Memories
      </motion.h1>
      <motion.p
        className="text-lg sm:text-2xl tracking-tighter sm:tracking-normal whitespace-pre-wrap text-center mx-auto text-primary-950/80 cursor-default sm:max-w-[70%] max-w-xs"
        variants={animationVariants}
        initial={firstLoading ? "initial" : ""}
        animate={firstLoading ? "animate" : ""}
        transition={{
          delay: 5 * transitionDelay,
          duration: transitionDuration * 2,
        }}
        key="home-p"
      >
        Collect, share and remember your adventures in a space that celebrates
        the essence of exploration.
      </motion.p>
      <motion.div
        className="flex-col gap-2 flex w-fit sm:gap-10 sm:flex-row"
        variants={animationVariants}
        initial={firstLoading ? "initial" : ""}
        animate={firstLoading ? "animate" : ""}
        transition={{
          delay: 5 * transitionDelay,
          duration: transitionDuration * 2,
        }}
      >
        <NavLink to={"/login"}>
          <motion.button
            className="bg-action-400 w-40 sm:w-52 hover:bg-action-500 sm:px-10 py-4 sm:text-xl rounded-2xl tracking-tight font-bold text-background-50  shadow-md transition-colors"
            whileHover={{ scale: 1.03 }}
            whileTap={{ scale: 1.01 }}
            onClick={() => {
              if (firstLoading) setFirstLoading(false);
            }}
          >
            Get Started
          </motion.button>
        </NavLink>
        <NavLink to={"/public-memories"}>
          <motion.button
            className=" bg-background-50 w-40 sm:w-52 hover:bg-background-100 sm:px-10 py-4 sm:text-xl rounded-2xl tracking-tight font-bold text-primary-950 shadow-md transition-colors"
            whileHover={{ scale: 1.03 }}
            whileTap={{ scale: 1.01 }}
            onClick={() => {
              if (firstLoading) setFirstLoading(false);
            }}
          >
            Discover
          </motion.button>
        </NavLink>
      </motion.div>
    </div>
  );
}

export default HomePageBanner;
