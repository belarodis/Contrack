import React, { useCallback, useEffect, useRef, useState } from "react";
import "./FormDropdown.css";

export type Option = { value: string; label: string };

type ModalDropdownFormProps = {
    title?: string;
    options: Option[];
    initialValue?: string;
    confirmText?: string;
    cancelText?: string;
    onClose?: () => void;
    onSave?: (value: string) => void;
};

export default function ModalDropdownForm({
                                              title = "Selecionar Opção",
                                              options,
                                              initialValue = "",
                                              confirmText = "Salvar",
                                              cancelText = "Cancelar",
                                              onClose,
                                              onSave,
                                          }: ModalDropdownFormProps) {
    const [value, setValue] = useState(initialValue);
    const [isOpen, setIsOpen] = useState(true);
    const modalRef = useRef<HTMLDivElement>(null);
    const firstFocusableRef = useRef<HTMLButtonElement>(null);

    const canSave = value.trim().length > 0;

    // Close helpers
    const close = useCallback(() => {
        setIsOpen(false);
        onClose?.();
    }, [onClose]);

    // Keyboard: ESC to close; Enter to save when possible
    useEffect(() => {
        const handler = (e: KeyboardEvent) => {
            if (e.key === "Escape") {
                e.preventDefault();
                close();
            }
            if ((e.key === "Enter" || e.key === "NumpadEnter") && canSave) {
                e.preventDefault();
                onSave?.(value);
                close();
            }
        };
        document.addEventListener("keydown", handler);
        return () => document.removeEventListener("keydown", handler);
    }, [canSave, value, onSave, close]);

    // Autofocus first focusable
    useEffect(() => {
        firstFocusableRef.current?.focus();
    }, []);

    // Click outside to close
    const onOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
        if (e.target === e.currentTarget) {
            close();
        }
    };

    if (!isOpen) return null;

    return (
        <div
            className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm"
            onMouseDown={onOverlayClick}
            aria-modal="true"
            role="dialog"
            aria-labelledby="modal-title"
        >
            <div
                ref={modalRef}
                className="w-[600px] max-w-[94vw] rounded-2xl border border-teal-300/60 bg-emerald-900 text-teal-50 shadow-2xl ring-1 ring-white/10"
                onMouseDown={(e) => e.stopPropagation()}
            >
                {/* Header */}
                <header className="flex items-center justify-between gap-4 border-b border-white/10 p-5">
                    <h2 id="modal-title" className="text-2xl font-extrabold tracking-tight">
                        {title}
                    </h2>
                    <div className="flex items-center gap-2">
                        <button
                            ref={firstFocusableRef}
                            onClick={close}
                            className="rounded-xl border border-white/10 px-3 py-2 text-sm font-semibold hover:bg-white/10 active:scale-[.98]"
                            aria-label={cancelText}
                        >
                            {cancelText}
                        </button>
                        <button
                            onClick={() => {
                                if (canSave) {
                                    onSave?.(value);
                                    close();
                                }
                            }}
                            disabled={!canSave}
                            className="rounded-xl bg-teal-400/90 px-3 py-2 text-sm font-bold text-emerald-950 shadow hover:bg-teal-300 disabled:cursor-not-allowed disabled:opacity-40"
                        >
                            {confirmText}
                        </button>
                    </div>
                </header>

                {/* Body */}
                <div className="grid gap-5 p-5">
                    <label className="grid gap-2">
            <span className="text-sm font-medium uppercase tracking-wide text-teal-200">
              Opção
            </span>

                        {/* Dropdown cápsula branca */}
                        <div className="dropdown">
                            <select
                                value={value}
                                onChange={(e) => setValue(e.target.value)}
                                className="dropdown__native"
                            >
                                <option value="" disabled>
                                    Selecione…
                                </option>
                                {options.map((opt) => (
                                    <option key={opt.value} value={opt.value}>
                                        {opt.label}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </label>

                    {/* Example helper text */}
                    <p className="text-sm text-teal-200/80">
                        Dica: use <kbd className="rounded bg-black/30 px-1">Enter</kbd> para salvar e
                        <kbd className="ml-1 rounded bg-black/30 px-1">Esc</kbd> para fechar.
                    </p>
                </div>
            </div>
        </div>
    );
}

// --- Example usage (remove before using in production) ---
export function DemoModalHost() {
    const [open, setOpen] = useState(false);
    const [chosen, setChosen] = useState<string>("");

    const options: Option[] = [
        { value: "design", label: "Design" },
        { value: "dev", label: "Desenvolvimento" },
        { value: "qa", label: "Qualidade (QA)" },
        { value: "ops", label: "Operações" },
    ];

    return (
        <div className="grid place-items-center gap-4 p-8">
            <button
                onClick={() => setOpen(true)}
                className="rounded-2xl bg-emerald-800 px-5 py-3 font-bold text-teal-100 shadow hover:bg-emerald-700"
            >
                Abrir Modal
            </button>

            {chosen && (
                <div className="text-sm text-teal-200">
                    Selecionado: <span className="font-bold text-teal-100">{chosen}</span>
                </div>
            )}

            {open && (
                <ModalDropdownForm
                    title="Criar Projeto — Categoria"
                    options={options}
                    onClose={() => setOpen(false)}
                    onSave={(v) => {
                        setChosen(v);
                        setOpen(false);
                    }}
                />
            )}
        </div>
    );
}