import { useState } from "react";
import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";
import { type Option } from "../forms/FormDropdown";
import { usePessoas } from "../../hooks/usePessoas";
import { usePerfis } from "../../hooks/usePerfis";
import { createAlocacao } from "../../services/alocacoes.service";
import { toast } from "react-toastify";
import axios from "axios";

function containsOnlyDigits(str: string): boolean {
  return /^[0-9]+$/.test(str);
}

type CriarAlocacaoProps = {
  onClose?: () => void;
  projetoId: number | null;
};

export default function CriarAlocacao({
  onClose,
  projetoId,
}: CriarAlocacaoProps) {
  const [pessoa, setPessoa] = useState<string>("");
  const [perfil, setPerfil] = useState<string>("");
  const [horasSemanais, setHorasSemanais] = useState("");
  const [saving, setSaving] = useState(false);

  const pessoas = usePessoas();
  const pessoasOptions: Option[] = pessoas.map((p) => ({
    value: String(p.id),
    label: p.nome,
  }));

  const perfis = usePerfis();
  console.log(perfis.length);
  const perfisOptions: Option[] = perfis.map((p) => ({
    value: String(p.id),
    label: p.tipo,
  }));

  const canSave =
    pessoa.trim().length > 0 &&
    perfil.trim().length > 0 &&
    containsOnlyDigits(horasSemanais);

  async function handleSave() {
    if (!canSave || saving) return;
    setSaving(true);
    try {
      const dto = {
        horasSemana: Number(horasSemanais),
        pessoaId: Number(pessoa),
        projetoId: Number(projetoId),
        perfilId: Number(perfil),
      };

      await createAlocacao(dto); // POST
      toast.success("Alocação criada com sucesso! 🎉");
      onClose?.();
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        // tenta usar a mensagem vinda do back
        const msg =
          (err.response?.data as any)?.message || "Erro ao criar alocação.";
        toast.error(msg);
      } else {
        toast.error("Erro inesperado. 🤯");
      }
      console.error("Erro ao criar alocação:", err);
    } finally {
      setSaving(false);
    }
  }

  return (
    <FormCard title="Criar Alocação" onExit={onClose} onSave={handleSave}>
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

      <FormField label="Selecionar Perfil:" htmlFor="perfil">
        <div className="dropdown" style={{ width: "100%" }}>
          <select
            id="perfil"
            className="dropdown__native"
            value={perfil}
            onChange={(e) => setPerfil(e.target.value)}
          >
            <option value="" disabled>
              Selecione…
            </option>
            {perfisOptions.map((opt) => (
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
    </FormCard>
  );
}
