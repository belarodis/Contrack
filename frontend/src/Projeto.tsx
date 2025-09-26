import type { ProjetoStatus } from "./models/projeto";

interface ProjetoProps {
  id: number;
  nome: string;
  onSelect: (id: number | null) => void;
  selectedId: number | null;
  status: ProjetoStatus
}

function Projeto({ id, nome, onSelect, selectedId, status}: ProjetoProps) {
    const isSelected = id === selectedId;
    return (
        <div
            onClick={() => {isSelected ? onSelect(null) : onSelect(id)}}
            className={`flex flex-col justify-between ${
                isSelected
                    ? "bg-[#074E56] shadow-[0_0_20px_rgba(157,255,217,0.6)] outline-[#04FDE0]"
                    : "outline-transparent"
            } bg-[#0D3445] hover:cursor-pointer hover:shadow-[0_0_20px_rgba(157,255,217,0.6)] hover:outline-[#04FDE0] outline-[2px] transition-all duration-[0.3s] h-[22vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold`}
        >
            {/* Conteúdo do card */}
            <div className="flex flex-col gap-[5px] h-fit">
                <h3 className="text-[18px] font-bold">{nome}</h3>
                <div className="flex flex-row gap-[15px]">
                    <p className="text-[13px]">
                        <span className="text-[#C2CCD0]">Início: </span>22/09
                    </p>
                    <p className="text-[13px]">
                        <span className="text-[#C2CCD0]">Fim: </span>26/09
                    </p>
                </div>
                <p className="text-[13px]">
                    <span className="text-[#C2CCD0]">Descrição: </span>Lorem ipsum dolor sit
                    amet consectetur adipiscing elit.
                </p>
            </div>

            <div
                className={`w-[50px] h-[15px] rounded-[10px] mb-[10px] ${
                    status === "Ativo"
                        ? "bg-[#6ECD84]"
                        : status === "Finalizado"
                            ? "bg-[#3D5D6A]"
                            : status === "Incompleto"
                                ? "bg-[#CF6868]"
                                : "bg-[#DEB953]"
                }`}
            />
        </div>
    );
}

export default Projeto;
