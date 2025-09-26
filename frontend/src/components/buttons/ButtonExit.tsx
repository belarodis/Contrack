// ButtonExit.tsx
import React from "react";

type ButtonExitProps = {
  onClick?: React.MouseEventHandler<HTMLDivElement>;
  className?: string;
};

export default function ButtonExit({
  onClick,
  className = "",
}: ButtonExitProps) {
  return (
    <div
      onClick={onClick}
      className={
        "flex h-fit bg-[#734A4A] p-[11px] rounded-[15px] mr-[15px] " +
        "hover:shadow-[0_0_20px_rgba(225,0,0,0.5)] hover:cursor-pointer " +
        "outline-transparent hover:outline-[#FF9D9D] outline-[1px] " +
        "transition-all duration-[0.3s] " +
        className
      }
      role="button"
      tabIndex={0}
      onKeyDown={(e) =>
        (e.key === "Enter" || e.key === " ") && onClick?.(e as never)
      }
    >
      <img src="/exit-icon.svg" alt="exit" className="flex" />
    </div>
  );
}
