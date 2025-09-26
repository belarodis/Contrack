import { useState } from "react";
import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";
import { createProjetos } from "../../services/projeto.service";
import { toast } from "react-toastify";
import axios from "axios";

export default function CriarProjeto({ onClose }: { onClose?: () => void }) {
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [inicio, setInicio] = useState<string>("");
  const [fim, setFim] = useState<string>("");
  const [saving, setSaving] = useState(false);

  const canSave = nome.trim().length >= 2 && !!inicio && !!fim && inicio <= fim;

  async function handleSave() {
    if (!canSave || saving) return;
    setSaving(true);
    try {
      const dto = {
        nome,
        dataInicio: inicio, // YYYY-MM-DD
        dataFim: fim, // YYYY-MM-DD
        descricao,
      };

      await createProjetos(dto); // POST
      toast.success("Projeto criado com sucesso! 🎉");
      onClose?.();
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const status = err.response?.status;
        const msg = (err.response?.data as any)?.message;

        if (status === 409) {
          toast.error(msg ?? "Esse projeto conflita com um existente.");
        } else if (status === 500) {
          toast.error("Já existe um projeto com esse nome.");
        } else {
          toast.error(
            `Erro${status ? ` ${status}` : ""}: ${msg ?? "Falha inesperada."}`
          );
        }
      } else {
        toast.error("Erro inesperado. 🤯");
      }
      console.error("Erro ao criar projeto:", err);
    } finally {
      setSaving(false);
    }
  }

  return (
    <FormCard title="Criar Projeto" onExit={onClose} onSave={handleSave}>
      <FormField label="Nome" htmlFor="nome">
        <input
          id="nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          placeholder="Digite o nome"
        />
      </FormField>

      <FormField label="Descrição" htmlFor="desc">
        <input
          id="desc"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          placeholder="Descrição"
        />
      </FormField>

      <div className="form-period">
        <FormField
          label="Período"
          htmlFor="inicio"
          className="form-field--date"
        >
          <input
            id="inicio"
            type="date"
            value={inicio}
            onChange={(e) => setInicio(e.target.value)}
          />
        </FormField>

        <span className="form-period__sep">até</span>

        <FormField label=" " htmlFor="fim" className="form-field--date">
          <input
            id="fim"
            type="date"
            value={fim}
            onChange={(e) => setFim(e.target.value)}
          />
        </FormField>
      </div>
    </FormCard>
  );
}
