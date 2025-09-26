// src/components/form/FormField.tsx
import "./FormField.css";
import React from "react";
import "react-toastify/dist/ReactToastify.css";

type FormFieldProps = {
  label: string;
  htmlFor: string;
  hint?: string;
  children: React.ReactNode;
  className?: string;
};

export function FormField({
  label,
  htmlFor,
  hint,
  children,
  className = "",
}: FormFieldProps) {
  return (
    <div className={`form-field ${className}`}>
      <label htmlFor={htmlFor}>{label}</label>
      {children}
      {hint && <div className="form-field__hint">{hint}</div>}
    </div>
  );
}