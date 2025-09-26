import { useCustoProjeto } from "./hooks/useCustoProjeto.ts";

function Custos({ projetoId }: { projetoId: number | null }) {
    const custo = useCustoProjeto(projetoId);

    return (
        <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[48px] pt-[30px] basis-2/3 min-h-0">
                <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Custos</h1>
            <div className="flex flex-row gap-[15px]">
                <p className=" text-white text-[36px] font-semibold">
                    {custo !== null ? `R$ ${custo.toFixed(2)}` : "—"}
                </p>
            </div>

            <div className="grid grid-cols-2 gap-[16px] pt-[15px] h-full min-h-0 overflow-y-auto px-[10px]">
                {/* Aqui você pode listar custos detalhados no futuro */}
            </div>
        </div>
    );
}

export default Custos;