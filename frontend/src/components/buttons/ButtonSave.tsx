type ButtonSaveProps = React.HTMLAttributes<HTMLDivElement>;

export default function ButtonSave({ className = "", ...rest }: ButtonSaveProps) {
    return (
        <div
            {...rest}
            className={
                "flex h-fit bg-[#275059] p-[12px] rounded-[10px] mr-[15px] " +
                "hover:shadow-[0_0_20px_rgba(157,255,217,0.5)] hover:cursor-pointer " +
                "outline-transparent hover:outline-[#04FDE0] outline-[1px] " +
                "transition-all duration-[0.3s] " +
                className
            }
        >
            <img src="/save-icon.svg" alt="save" className="flex" />
        </div>
    );
}