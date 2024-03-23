import React, { useEffect } from "react";
import { motion } from "framer-motion";
import ImageCarousel from "../../components/home-page/ImageCarousel";
import { NavLink } from "react-router-dom";
import { useUserContext } from "../../context/UserContext";
import HomePageBanner from "../../components/home-page/HomePageBanner";

function HomePage() {
  const { LoginFromCookies: LoginCookies } = useUserContext();

  useEffect(() => {
    LoginCookies();
  }, [LoginCookies]);

  return (
    <div className="gap-20 sm:mt-0 mt-10 sm:gap-60 xl:gap-0 mx-auto h-[100vh] flex flex-col-reverse xl:flex-row items-center xl:justify-between px-4 sm:px-0 py-8 overflow-hidden bg-gradient-to-br from-background-50 to-background-300/50">
      <ImageCarousel />
      <HomePageBanner />
    </div>
  );
}

export default HomePage;
