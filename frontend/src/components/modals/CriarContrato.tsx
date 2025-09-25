import { useState } from "react";
import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";
import { type Option } from "../forms/FormDropdown";

function containsOnlyDigits(str: string): boolean {
    return /^[0-9]+$/.test(str);
}

export default function CriarContrato({ onClose }: { onClose?: () => void }) {
    const [pessoa, setPessoa] = useState<string>("");
    const [horasSemanais, setHorasSemanais] = useState("");
    const [inicio, setInicio] = useState<string>("");
    const [fim, setFim] = useState<string>("");
    const [salarioHora, setSalarioHora] = useState("");

    const pessoasOptions: Option[] = [
        { value: "gerente",  label: "Gerente" },
        { value: "dev",      label: "Dev" },
        { value: "qa",       label: "QA" },
        { value: "security", label: "Security" },
    ];

    const canSave =
        pessoa.trim().length > 0 &&
        horasSemanais.length > 0 &&
        containsOnlyDigits(horasSemanais);

    return (
        <FormCard
            title="Criar Contrato"
            onExit={onClose}
            onSave={() => {
                if (!canSave) return;
                console.log({ pessoa, horasSemanais, salarioHora, inicio, fim });
                onClose?.();
            }}
        >
            <FormField label="Selecionar Pessoa:" htmlFor="pessoa">
                <div className="dropdown" style={{ width: "100%" }}>
                    <select
                        id="pessoa"
                        className="dropdown__native"
                        value={pessoa}
                        onChange={(e) => setPessoa(e.target.value)}
                    >
                        <option value="" disabled>Selecione…</option>
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
                <FormField label="Período" htmlFor="inicio" className="form-field--date">
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