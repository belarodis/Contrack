
import "./CalendarFormField.css";
import React from "react";

type FormFieldProps = {
  label: string;
  htmlFor: string;
  hint?: string;
  children: React.ReactNode;
  className?: string;
};

export function CalendarFormField({
  label,
  htmlFor,
  hint,
  children,
  className = "",
}: FormFieldProps) {
  return (
    <div className={`calendar-form-field ${className}`}>
      <label htmlFor={htmlFor}>{label}</label>
      {children}
      {hint && <div className="calendar-form-field__hint">{hint}</div>}
    </div>
  );
}
