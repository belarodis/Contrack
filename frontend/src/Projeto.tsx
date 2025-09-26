import type { ProjetoStatus } from "./models/projeto";

interface ProjetoProps {
  id: number;
  nome: string;
  descricao: string;
  dataInicio: string;
  dataFim: string;
  onSelect: (id: number | null) => void;
  selectedId: number | null;
  status: ProjetoStatus;
}

function Projeto({
  id,
  nome,
  descricao,
  dataInicio,
  dataFim,
  onSelect,
  selectedId,
  status,
}: ProjetoProps) {
  const isSelected = id === selectedId;
  return (
    <div
      onClick={() => {
        isSelected ? onSelect(null) : onSelect(id);
      }}
      className={`flex flex-col justify-between ${
        isSelected
          ? "bg-[#074E56] shadow-[0_0_20px_rgba(157,255,217,0.6)] outline-[#04FDE0]"
          : "outline-transparent"
      } bg-[#0D3445] hover:cursor-pointer hover:shadow-[0_0_20px_rgba(157,255,217,0.6)] hover:outline-[#04FDE0] outline-[2px] transition-all duration-[0.3s] h-[22vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold`}
    >
      <div className="flex flex-col gap-[5px] h-fit">
        <div className="flex items-center gap-[10px]">
          <img
            src="/project-icon.svg"
            alt="plus"
            className="flex w-auto h-[17px]"
          />
          <h3 className="text-[18px] font-bold">{nome}</h3>
        </div>
        <div className="flex flex-row gap-[15px]">
          <p className="text-[12px]">
            <span className="text-[#C2CCD0]">Início: </span> {dataInicio}
          </p>
          <p className="text-[12px]">
            <span className="text-[#C2CCD0]">Fim: </span> {dataFim}
          </p>
        </div>
        <p className="text-[13px]">
          <span className="text-[#C2CCD0]">Descrição: </span>
          {descricao}
        </p>
      </div>

      <div
        className={`inline-flex items-center justify-center mt-[10px] px-[12px] py-[4px] rounded-[12px] text-[12px] font-bold w-fit self-start mb-[10px] ${
          status === "Ativo"
            ? "bg-[#6ECD84] text-[#0D3445]"
            : status === "Finalizado"
            ? "bg-[#3D5D6A] text-white"
            : status === "Incompleto"
            ? "bg-[#CF6868] text-white"
            : "bg-[#DEB953] text-[#0D3445]"
        }`}
      >
        {status}
      </div>
    </div>
  );
}

export default Projeto;
