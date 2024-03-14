import React, { useEffect, useRef, useState } from "react";
import { useSettingsContext } from "../../context/SettingsContext";
import { AnimatePresence, motion, useInView } from "framer-motion";
import { RxHamburgerMenu } from "react-icons/rx";
import { GoX } from "react-icons/go";
import NavbarLink from "./NavbarLink";
import { SlGlobe } from "react-icons/sl";
import { useNavbarLinks } from "../../hooks/useNavbarLinks";

function Navbar() {
  const ref = useRef(null);
  const isInView = useInView(ref);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
  const { setDisableScroll } = useSettingsContext();
  const { navbarLinks } = useNavbarLinks();
  const toggleMobileMenu = () => {
    setDisableScroll(!isMobileMenuOpen);
    setIsMobileMenuOpen((isMobileMenuOpen) => !isMobileMenuOpen);
  };
  useEffect(() => {
    if (isMobileMenuOpen && !isInView) setIsMobileMenuOpen(false);
  }, [isInView, isMobileMenuOpen]);

  return (
    <div
      className={`fixed left-0 top-0 z-20 flex w-screen flex-row items-center justify-center gap-8 bg-background-50 px-5 py-3 text-primary-950 shadow-sm sm:px-24 xl:px-60 text-lg`}
    >
      <div className="flex justify-center items-center gap-2 lg:gap-3 pointer-events-none">
        <SlGlobe className="text-xl lg:text-3xl " />
        <p className="sm:text-lg lg:text-2xl font-bold whitespace-nowrap tracking-tight">
          Travel Memories
        </p>
      </div>

      <div
        className={`hidden flex-row items-center justify-end gap-8 lg:flex w-full`}
      >
        {navbarLinks.map((n) => (
          <NavbarLink key={n.to} {...n} />
        ))}
      </div>
      <motion.button
        ref={ref}
        className="ml-auto cursor-pointer text-3xl lg:hidden"
        whileHover={{ scaleY: 1.1 }}
        onClick={toggleMobileMenu}
      >
        <RxHamburgerMenu />
      </motion.button>
      {/* Hamburger menu */}
      <AnimatePresence>
        {isInView && isMobileMenuOpen && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="absolute left-0 top-0 z-50 h-screen w-screen overflow-hidden bg-black/40"
          >
            <div
              className="absolute inset-0"
              onClick={(e) => {
                e.stopPropagation();
                toggleMobileMenu();
              }}
            />
            <motion.ul
              initial={{ x: 500 }}
              animate={{ x: 0 }}
              exit={{ x: 500 }}
              transition={{ duration: 0.1 }}
              className="absolute right-0 top-0 flex h-full w-fit flex-col items-end gap-4 bg-background-50 p-4 px-8 text-right text-2xl shadow-md"
            >
              <motion.button
                className="cursor-pointer text-4xl"
                whileHover={{ scale: 1.1 }}
                onClick={toggleMobileMenu}
              >
                <GoX />
              </motion.button>
              {navbarLinks.map((n) => (
                <div
                  key={n.to}
                  onClick={() => {
                    toggleMobileMenu();
                  }}
                >
                  <NavbarLink {...n} />
                </div>
              ))}
            </motion.ul>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}

export default Navbar;
