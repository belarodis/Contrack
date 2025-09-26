import { useState } from "react";
import { createPessoa } from "../../services/pessoas.service";
import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";

type CriarPessoaProps = {
  onClose?: () => void;
};

export function CriarPessoa({ onClose }: CriarPessoaProps) {
  const [nome, setNome] = useState("");
  const [saving, setSaving] = useState(false);

  const isValid = nome.trim().length >= 2;
  const canSave = isValid && !saving;

  async function handleSave() {
    if (!canSave) return;
    setSaving(true);
    try {
      await createPessoa({ nome }); // POST
      onClose?.();
    } catch (e) {
      console.error("Erro ao criar pessoa", e);
    } finally {
      setSaving(false);
    }
  }

  return (
    <div
      className="criarPessoa"
      onKeyDown={(e) => {
        if (e.key === "Enter" && canSave) handleSave();
      }}
    >
      <FormCard
        title="Criar Pessoa"
        onExit={onClose}
        onSave={handleSave}
      >
        <FormField
          label="Nome"
          htmlFor="nome"
          hint={!isValid && nome.length > 0 ? "Mínimo de 2 caracteres" : ""}
        >
          <input
            id="nome"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            placeholder="Digite o nome"
            autoFocus
          />
        </FormField>
        
        {saving && <p className="text-sm text-gray-400 mt-2">Salvando...</p>}
      </FormCard>
    </div>
  );
}
