import React from "react";
import ButtonExit from "../buttons/ButtonExit.tsx";
import ButtonSave from "../buttons/ButtonSave.tsx";
import "./FormCard.css";

type FormCardProps = {
  title: string;
  onExit?: () => void;
  onSave?: () => void;
  children: React.ReactNode;
};

export function FormCard({ title, onExit, onSave, children }: FormCardProps) {
  return (
    <section className="form-card">
      <header className="form-card__header">
        <h2 className="form-card__title">{title}</h2>
        <div className="form-card__actions">
          <ButtonExit onClick={onExit} />
          <ButtonSave onClick={onSave} />
        </div>
      </header>

      <div className="form-card__body">{children}</div>
    </section>
  );
}
