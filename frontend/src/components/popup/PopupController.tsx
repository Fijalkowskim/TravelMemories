import React, { useEffect } from "react";
import { usePopupContext } from "../../context/PopupContext";
import PopupCard from "./PopupCard";
import { AnimatePresence, motion } from "framer-motion";
import { PopupMessageType } from "../../models/popups/PopupMessageType";

function PopupController() {
  const { messages} = usePopupContext();

  return (
    <motion.ul
      layout
      className="absolute w-full mt-16 pointer-events-none flex flex-col justify-center items-end gap-2 overflow-hidden py-4"
    >
      <AnimatePresence>
        {messages.map((mess) => (
          <PopupCard key={mess.id} popupMessage={mess} />
        ))}
      </AnimatePresence>
    </motion.ul>
  );
}

export default PopupController;
