import React, { ButtonHTMLAttributes, ReactNode } from "react";
import { cn } from "../../helpers/helpers";
import { MotionProps, motion } from "framer-motion";
import { VariantProps, cva } from "class-variance-authority";

const variants = cva(
  "flex items-center justify-center px-2 py-1 rounded-md shadow-sm transition-colors text-center mx-auto",
  {
    variants: {
      variant: {
        default: "bg-action-400 hover:bg-action-500 text-background-50",
        action: "bg-action-200 hover:bg-action-300",
        actionDark: "bg-action-300 hover:bg-action-400",
        edit: "bg-action-200 hover:bg-action-300 gap-1 rounded-full px-3",
        delete: "bg-red-200 hover:bg-red-300 gap-1 rounded-full px-3",
      },
    },
    defaultVariants: {
      variant: "default",
    },
  }
);

interface Props
  extends Omit<
      ButtonHTMLAttributes<HTMLButtonElement>,
      "onDragStart" | "onAnimationStart" | "onDrag" | "onDragEnd" | "style"
    >,
    VariantProps<typeof variants>,
    MotionProps {
  parentClass?: string;
  className?: string;
  children?: ReactNode;
  rounded?: boolean;
  disableScaleAnimation?: boolean;
}

function CustomButton({
  parentClass,
  className,
  children,
  variant,
  rounded,
  disableScaleAnimation,
  ...props
}: Props) {
  return (
    <motion.button
      {...props}
      whileHover={disableScaleAnimation ? {} : { scale: 1.03 }}
      whileTap={disableScaleAnimation ? {} : { scale: 1.01 }}
      className={cn(
        variants({ variant, className }),
        `${rounded ? "rounded-full" : ""}`
      )}
    >
      {children}
    </motion.button>
  );
}
export default CustomButton;
