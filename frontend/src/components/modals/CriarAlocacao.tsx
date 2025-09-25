import { useState } from "react";
import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";
import { type Option } from "../forms/FormDropdown";

function containsOnlyDigits(str: string): boolean {
    return /^[0-9]+$/.test(str);
}

export default function CriarAlocacao({ onClose }: { onClose?: () => void }) {
    const [pessoa, setPessoa] = useState<string>("");
    const [projeto, setProjeto] = useState<string>("");
    const [perfil,  setPerfil] = useState<string>("");
    const [horasSemanais, setHorasSemanais] = useState("");

    const pessoasOptions: Option[] = [
        { value: "Pessoa 1",  label: "Pessoa 1" },
        { value: "Pessoa 2",      label: "Pessoa 2" },
        { value: "Pessoa 3",       label: "Pessoa 3" },
        { value: "Pessoa 4", label: "Pessoa 4" },
    ];

    const projetosOptions: Option[] = [
        { value: "Projeto 1",  label: "Projeto 1" },
        { value: "Projeto 2",  label: "Projeto 2" },
        { value: "Projeto 3",  label: "Projeto 3" },
        { value: "Projeto 4",  label: "Projeto 4" },
    ];

    const perfisOptions: Option[] = [
        { value: "gerente",  label: "Gerente" },
        { value: "dev",      label: "Dev" },
        { value: "qa",       label: "QA" },
        { value: "security", label: "Security" },
    ];

    const canSave =
        pessoa.trim().length > 0 &&
        projeto.trim().length > 0 &&
        perfil.trim().length > 0 &&
        containsOnlyDigits(horasSemanais);

    return (
        <FormCard
            title="Criar Alocação"
            onExit={onClose}
            onSave={() => {
                if (!canSave) return;
                console.log({ pessoa, projeto, perfil, horasSemanais });
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

            <FormField label="Selecionar Projeto:" htmlFor="projeto">
                <div className="dropdown" style={{ width: "100%" }}>
                    <select
                        id="projeto"
                        className="dropdown__native"
                        value={projeto}
                        onChange={(e) => setProjeto(e.target.value)}
                    >
                        <option value="" disabled>Selecione…</option>
                        {projetosOptions.map((opt) => (
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
                        <option value="" disabled>Selecione…</option>
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