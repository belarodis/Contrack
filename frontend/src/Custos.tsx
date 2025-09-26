import { useEffect, useState } from "react";
import { useProjeto } from "./hooks/useProjeto";
import { useCustoProjeto } from "./hooks/useCustoProjeto";
import { useCustoPeriodo } from "./hooks/useCustoPeriodo";
import { CalendarFormField } from "./components/forms/CalendarFormField";

function Custos({ projetoId }: { projetoId: number | null }) {
    const projeto = useProjeto(projetoId);
    const custoSemanal = useCustoProjeto(projetoId);

    const [inicio, setInicio] = useState("");
    const [fim, setFim] = useState("");

    // quando trocar de projeto, preenche datas padrão
    useEffect(() => {
        if (!projeto) { setInicio(""); setFim(""); return; }
        setInicio(projeto.dataInicio); // já vem YYYY-MM-DD
        setFim(projeto.dataFim);
    }, [projeto]);

    const custoPeriodo = useCustoPeriodo(projetoId, inicio, fim);

    const fmt = (n: number) =>
        `R$ ${n.toLocaleString("pt-BR", { minimumFractionDigits: 2 })}`;

    return (
        <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[32px] pt-[25px] pb-[20px] basis-1/3 min-h-0">
            <div className="flex items-center justify-between mb-[20px]">
                <h1 className="text-[#9DFFD9] text-[32px] font-semibold">Custos</h1>

                <div className="form-period">
                    <CalendarFormField label="" htmlFor="inicio">
                        <input
                            id="inicio"
                            type="date"
                            value={inicio}
                            onChange={(e) => setInicio(e.target.value)}
                        />
                    </CalendarFormField>
                    <span className="form-period__sep">até</span>
                    <CalendarFormField label="" htmlFor="fim">
                        <input
                            id="fim"
                            type="date"
                            value={fim}
                            onChange={(e) => setFim(e.target.value)}
                        />
                    </CalendarFormField>
                </div>
            </div>

            <div className="flex flex-row gap-[20px]">
                <div className="flex flex-col bg-[#0D3445] rounded-[16px] px-[20px] py-[15px] flex-1">
                    <p className="text-[#9DFFD9] text-[18px] font-medium mb-[5px]">Geral:</p>
                    <p className="text-white text-[28px] font-bold">
                        {custoSemanal != null ? fmt(custoSemanal) : "—"}
                    </p>
                </div>

                <div className="flex flex-col bg-[#0D3445] rounded-[16px] px-[20px] py-[15px] flex-1">
                    <p className="text-[#9DFFD9] text-[18px] font-medium mb-[8px]">Por período:</p>
                    <p className="text-white text-[28px] font-bold">
                        {custoPeriodo != null ? fmt(custoPeriodo) : "—"}
                    </p>
                </div>
            </div>
        </div>
    );
}

export default Custos;