import { useState } from "react";
import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";
import { type Option } from "../forms/FormDropdown";
import { usePessoas } from "../../hooks/usePessoas";
import { toast } from "react-toastify";
import { createContrato } from "../../services/contratos.service"; // 👈 importa o service
import axios from "axios";

function containsOnlyDigits(str: string): boolean {
  return /^[0-9]+$/.test(str);
}

export default function CriarContrato({ onClose }: { onClose?: () => void }) {
  const [pessoa, setPessoa] = useState<string>("");
  const [horasSemanais, setHorasSemanais] = useState("");
  const [inicio, setInicio] = useState<string>("");
  const [fim, setFim] = useState<string>("");
  const [salarioHora, setSalarioHora] = useState("");
  const [saving, setSaving] = useState(false);

  // hook retorna PessoaViewDTO[]
  const pessoas = usePessoas();
  const pessoasOptions: Option[] = pessoas.map((p) => ({
    value: String(p.id),
    label: p.nome,
  }));

  const canSave =
    pessoa.trim().length > 0 &&
    horasSemanais.length > 0 &&
    containsOnlyDigits(horasSemanais) &&
    salarioHora.length > 0 &&
    inicio.trim().length > 0;

  async function handleSave() {
    if (!canSave || saving) return;
    setSaving(true);
    try {
      const dto = {
        dataInicio: inicio,
        dataFim: fim,
        horasSemana: Number(horasSemanais),
        salarioHora: Number(salarioHora),
        pessoaId: Number(pessoa),
      };
      await createContrato(dto); // POST
      onClose?.();
      toast.success("Contrato criado com sucesso!");
    } catch (err) {
      if (axios.isAxiosError(err)) {
        const status = err.response?.status;
        const msg =
          (err.response?.data as any)?.message ?? "Erro ao criar contrato.";
        if (status === 409) {
          toast.error(msg); // vai mostrar "O novo contrato se sobrepõe a um contrato existente."
        } else {
          toast.error(`Erro${status ? ` ${status}` : ""}: ${msg}`);
        }
      } else {
        toast.error("Erro inesperado. 🤯");
      }
    } finally {
      setSaving(false);
    }
  }

  return (
    <FormCard
      title="Criar Contrato"
      onExit={onClose}
      onSave={handleSave}
      // saveDisabled={!canSave || saving} // se ButtonSave aceitar
    >
      <FormField label="Selecionar Pessoa:" htmlFor="pessoa">
        <div className="dropdown" style={{ width: "100%" }}>
          <select
            id="pessoa"
            className="dropdown__native"
            value={pessoa}
            onChange={(e) => setPessoa(e.target.value)}
          >
            <option value="" disabled>
              Selecione…
            </option>
            {pessoasOptions.map((opt) => (
              <option key={opt.value} value={opt.value}>
                {opt.label}
              </option>
            ))}
          </select>
        </div>
      </FormField>

      <FormField label="Horas semanais" htmlFor="hs">
        <input
          id="hs"
          value={horasSemanais}
          onChange={(e) => setHorasSemanais(e.target.value)}
          placeholder="Ex.: 40"
          inputMode="numeric"
        />
      </FormField>

      <FormField label="Salário por Hora" htmlFor="sal">
        <input
          id="sal"
          value={salarioHora}
          onChange={(e) => setSalarioHora(e.target.value)}
          placeholder="Ex.: 75.00"
          inputMode="decimal"
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

      {saving && <p className="text-sm text-gray-400 mt-2">Salvando...</p>}
    </FormCard>
  );
}
