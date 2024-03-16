import Reactfrom, { useEffect, useState } from "react";
import { motion, useAnimation } from "framer-motion";
import { cn } from "../../helpers/helpers";
import CustomButton from "../general/CustomButton";
import { NavLink, useLocation } from "react-router-dom";
import { NavbarLinkProps } from "./NavbarLinkProps";
import { transform } from "typescript";

function NavbarLink(data: NavbarLinkProps) {
  const { pathname } = useLocation();
  const [active, setActive] = useState(false);
  useEffect(() => {
    setActive(pathname === data.to);
  }, [pathname, data.to]);
  return (
    <NavLink to={data.to}>
      <motion.button
        className={cn(
          `group ${
            data.button
              ? "text-primary-950"
              : " text-primary-700/80 hover:text-primary-900 transition-none"
          }  relative w-fit flex-shrink-0 cursor-pointer transition-all ${
            active && "text-primary-950"
          }  `,
          data.className
        )}
        initial={{ scale: 1 }}
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 1.03 }}
        transition={{ duration: 0.01 }}
      >
        {data.button ? (
          <CustomButton
            className={data.buttonClassName}
            parentClass={data.parentClassName}
          >
            {data.name}
          </CustomButton>
        ) : (
          <>
            {data.name}
            <div
              className={`h-[1px] w-full  -mt-1 transition-transform origin-center
                ${
                  active
                    ? "scale-100 bg-primary-950/70"
                    : "group-hover:scale-100 scale-0 bg-primary-950/70"
                }`}
            ></div>
          </>
        )}
      </motion.button>
    </NavLink>
  );
}

export default NavbarLink;
