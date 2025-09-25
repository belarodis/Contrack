import { createPortal } from "react-dom";
import { type ReactNode, useEffect } from "react";

type ModalProps = {
    open: boolean;
    onClose: () => void;
    children: ReactNode;
}


export function Modal({ open, onClose, children }: ModalProps) {
    useEffect(() => {
        if (!open) return;

        const onKey = (e: KeyboardEvent) => e.key === "Escape" && onClose();
        document.addEventListener("keydown", onKey);

        // scroll lock básico
        const prev = document.body.style.overflow;
        document.body.style.overflow = "hidden";

        return () => {
            document.removeEventListener("keydown", onKey);
            document.body.style.overflow = prev;
        };
    }, [open, onClose]);

    if (!open) return null;

    return createPortal(
        <div
            role="dialog"
            aria-modal="true"
            className="fixed inset-0 grid place-items-center bg-black/45 backdrop-blur-[2px] z-[9999]"
            onClick={onClose}
        >
            <div onClick={(e) => e.stopPropagation()}>
                {children}
            </div>
        </div>,
        document.body
    );
}