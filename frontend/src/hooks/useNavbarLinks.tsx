import { useEffect, useState } from "react";
import { NavbarLinkProps } from "../components/navbar/NavbarLinkProps";
import { useUserContext } from "../context/UserContext";

const loggedLinks: NavbarLinkProps[] = [
  { name: "New Memory", to: "/", button: true },
  { name: "Your Travels", to: "/travels" },
  { name: "Discover", to: "/public-memories" },
  { name: "Profile", to: "/profile" },
];
const unloggedLinks: NavbarLinkProps[] = [
  {
    name: "Log in",
    to: "/login",
    button: true,
    className: "max-w-[7rem] w-screen",
    buttonClassName: "w-full",
  },
  {
    name: "Register",
    to: "/register",
    button: true,
    className: "max-w-[7rem] w-screen",
    buttonClassName: "bg-orange-400 hover:bg-orange-500 w-full",
  },
];

export const useNavbarLinks = () => {
  const { isLoggedIn } = useUserContext();
  const [navbarLinks, setNavbarLinks] = useState<NavbarLinkProps[]>([]);

  useEffect(() => {
    setNavbarLinks(isLoggedIn ? loggedLinks : unloggedLinks);
  }, [isLoggedIn, setNavbarLinks]);

  return { navbarLinks };
};
