import { useState } from "react";
import { useCustoProjeto } from "./hooks/useCustoProjeto.ts";
import { FormField } from "./components/forms/FormField.tsx";

function Custos({ projetoId }: { projetoId: number | null }) {
    const custo = useCustoProjeto(projetoId);

    const [inicio, setInicio] = useState<string>("");
    const [fim, setFim] = useState<string>("");

    return (
        <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[32px] pt-[25px] pb-[20px] basis-2/3 min-h-0">
            {/* Header */}
            <div className="flex flex-row items-center justify-between mb-[20px]">
                <h1 className="text-[#9DFFD9] text-[32px] font-semibold">Custos</h1>

                {/* Seleção de período */}
                <div className="form-period">
                    <FormField label="" htmlFor="inicio" className="form-field--date">
                        <input
                            id="inicio"
                            type="date"
                            value={inicio}
                            onChange={(e) => setInicio(e.target.value)}
                        />
                    </FormField>

                    <span className="form-period__sep">até</span>

                    <FormField label="" htmlFor="fim" className="form-field--date">
                        <input
                            id="fim"
                            type="date"
                            value={fim}
                            onChange={(e) => setFim(e.target.value)}
                        />
                    </FormField>
                </div>
            </div>

            {/* Cards lado a lado */}
            <div className="flex flex-col gap-[20px]">
                {/* Card Geral */}
                <div className="flex flex-col bg-[#0D3445] rounded-[16px] px-[20px] py-[15px] flex-1">
                    <p className="text-[#9DFFD9] text-[18px] font-medium mb-[5px]">Custo Total:</p>
                    <p className="text-white text-[28px] font-bold">
                        {custo !== null
                            ? `R$ ${custo.toLocaleString("pt-BR", {
                                minimumFractionDigits: 2,
                            })}`
                            : "—"}
                    </p>
                </div>

                {/* Card Por Período */}
                <div className="flex flex-col bg-[#0D3445] rounded-[16px] px-[20px] py-[15px] flex-1">
                    <p className="text-[#9DFFD9] text-[18px] font-medium mb-[5px]">
                        Por período:
                    </p>
                    <p className="text-white text-[28px] font-bold">R$ 6.341,00</p>
                </div>
            </div>
        </div>
    );
}

export default Custos;