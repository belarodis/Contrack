import { FormCard } from "../forms/FormCard";
import { FormField } from "../forms/FormField";
import {useState} from "react";

export function CriarPessoa({ onClose }: { onClose?: () => void }) {
    const [nome, setNome] = useState("");
    const isValid = nome.trim().length >= 2;

    return (
        <div className="criarPessoa">
            <FormCard
                title="Criar Pessoa"
                onExit={onClose}
                onSave={() => {
                    if (!isValid) return;
                    console.log("Salvar pessoa:", nome);
                    onClose?.();
                }}
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
                    />
                </FormField>
            </FormCard>
        </div>
    );
}