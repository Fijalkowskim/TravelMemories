import React, { ReactNode } from "react";
import { cn } from "../helpers/helpers";

interface Props {
  children?: ReactNode;
  className?: string;
  alignColStart?: boolean;
}

const PageWrapper = (props: Props) => {
  return (
    <div
      className={cn(
        `p-2 pt-16 min-h-screen w-full ${
          props.alignColStart ? "flex flex-col justify-start items-center" : ""
        }`,
        props.className
      )}
    >
      {props.children}
    </div>
  );
};

export default PageWrapper;
