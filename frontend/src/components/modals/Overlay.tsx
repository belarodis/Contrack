import React, { useEffect } from "react";
import ReactDOM from "react-dom";

type OverlayProps = React.PropsWithChildren<{
    onClose: () => void;
}>;

export default function Overlay({ onClose, children }: OverlayProps) {
    useEffect(() => {
        const onKey = (e: KeyboardEvent) => e.key === "Escape" && onClose();
        document.addEventListener("keydown", onKey);
        // tranca o scroll do body
        const original = document.body.style.overflow;
        document.body.style.overflow = "hidden";
        return () => {
            document.removeEventListener("keydown", onKey);
            document.body.style.overflow = original;
        };
    }, [onClose]);

    return ReactDOM.createPortal(
        <div
            onClick={onClose}
            style={{
                position: "fixed",
                inset: 0,
                display: "grid",
                placeItems: "center",
                background: "rgba(0,0,0,.45)",
                backdropFilter: "blur(2px)",
                zIndex: 9999,
            }}
        >
            <div onClick={(e) => e.stopPropagation()}>{children}</div>
        </div>,
        document.body
    );
}