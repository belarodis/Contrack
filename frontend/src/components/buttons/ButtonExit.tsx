// ButtonExit.tsx
type ButtonExitProps = {
    onClick?: React.MouseEventHandler<HTMLDivElement>;
    className?: string;
};

export default function ButtonExit({ onClick, className = "" }: ButtonExitProps) {
    return (
        <div
            onClick={onClick}
            className={
                "flex h-fit bg-[#6e1919] p-[12px] rounded-[10px] mr-[15px] " +
                "hover:shadow-[0_0_20px_rgba(225,0,0,0.5)] hover:cursor-pointer " +
                "outline-transparent hover:outline-[#FD0408] outline-[1px] " +
                "transition-all duration-[0.3s] " +
                className
            }
            role="button"
            tabIndex={0}
            onKeyDown={(e) => (e.key === "Enter" || e.key === " ") && onClick?.(e as any)}
        >
            <img src="/exit-icon.svg" alt="exit" className="flex" />
        </div>
    );
}