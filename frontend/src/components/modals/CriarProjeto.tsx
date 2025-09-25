import { useState } from "react";
import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";

export default function CriarProjeto({ onClose }: { onClose?: () => void }) {
    const [nome, setNome] = useState("");
    const [descricao, setDescricao] = useState("");
    const [inicio, setInicio] = useState<string>("");
    const [fim, setFim] = useState<string>("");

    const canSave = nome.trim().length >= 2 && (!!inicio && !!fim && inicio <= fim);

    return (
        <FormCard
            title="Criar Projeto"
            onExit={onClose}
            onSave={() => {
                if (!canSave) return;
                console.log({ nome, descricao, inicio, fim });
                onClose?.();
            }}
        >
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