import React from "react";
import { AiOutlineLoading3Quarters } from "react-icons/ai";
import { cn } from "../../helpers/helpers";

type Props = {
  className?: string;
};

function LoadingSpinner({ className }: Props) {
  return (
    <div
      className={cn(
        "w-full h-full flex items-center justify-center",
        className
      )}
    >
      <AiOutlineLoading3Quarters className="animate-spin" />
    </div>
  );
}

export default LoadingSpinner;
