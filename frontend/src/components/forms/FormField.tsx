// components/form/FormField.tsx
import React from "react";
import "./FormField.css"

type FormFieldProps = {
    label: string;
    htmlFor: string;
    hint?: string;
    children: React.ReactNode; // o input
};

export function FormField({ label, htmlFor, hint, children }: FormFieldProps) {
    return (
        <div className="form-field">
            <label htmlFor={htmlFor} className="text-[var(--card-muted)] font-semibold">
                {label}
            </label>
            {children}
            {hint && <div className="text-xs text-[#ffd3d3]">{hint}</div>}
        </div>
    );
}