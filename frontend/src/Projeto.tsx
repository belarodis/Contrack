interface ProjetoProps {
  id: number;
  nome: string;
  onSelect: (id: number | null) => void;
  selectedId: number | null;
}

function Projeto({ id, nome, onSelect, selectedId }: ProjetoProps) {
  const isSelected = id === selectedId;
  return (
    <div
      onClick={() => {isSelected ? onSelect(null) : onSelect(id)}}
      className={`flex flex-col ${
        isSelected
          ? "bg-[#074E56] shadow-[0_0_20px_rgba(157,255,217,0.6)] outline-[#04FDE0] "
          : "outline-transparent"
      } bg-[#0D3445] hover:cursor-pointer hover:shadow-[0_0_20px_rgba(157,255,217,0.6)] hover:outline-[#04FDE0]  outline-[2px] transition-all duration-[0.3s] h-[18vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold `}
    >
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
  );
}

export default Projeto;
