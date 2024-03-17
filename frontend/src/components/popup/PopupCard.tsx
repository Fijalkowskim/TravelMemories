import React from "react";
import { motion } from "framer-motion";
import { cn } from "../../helpers/helpers";
import { PopupMessage } from "../../models/popups/PopupMessage";
import { PopupMessageType } from "../../models/popups/PopupMessageType";
import { usePopupContext } from "../../context/PopupContext";

const variants: Map<PopupMessageType, string> = new Map([
  [PopupMessageType.INFO, "bg-primary-50"],
  [PopupMessageType.ERROR, "bg-red-300"],
  [PopupMessageType.SUCCESS, "bg-green-300"],
]);

interface Props {
  popupMessage: PopupMessage;
}

const PopupCard = ({ popupMessage }: Props) => {
  const variant = variants.get(popupMessage.type);
  const { removeMessage } = usePopupContext();
  const handleClick = () => {
    removeMessage(popupMessage.id);
  };
  return (
    <motion.li
      initial={{ x: 500 }}
      animate={{ x: 0, transition: { duration: 0.3 } }}
      exit={{ x: 500 }}
      className={cn(
        "relative p-4 rounded-md shadow-md bg-primary-50 w-full max-w-xs overflow-hidden z-[999] pl-7",
        variant
      )}
    >
      There was an error
      <motion.button
        className="absolute -top-[0.15rem] left-2 text-xl cursor-pointer pointer-events-auto"
        whileHover={{ scale: 1.2 }}
        onClick={handleClick}
      >
        x
      </motion.button>
    </motion.li>
  );
};

export default PopupCard;
