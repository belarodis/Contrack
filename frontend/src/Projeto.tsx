interface ProjetoProps {
  id: number;
  nome: string;
  onSelect: (id: number) => void;
}

function Projeto({ id, nome, onSelect }: ProjetoProps) {
  return (
    <div
      onClick={() => onSelect(id)}
      className="flex flex-col bg-[#0D3445] h-[18vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold hover:cursor-pointer hover:shadow-[0_0_20px_rgba(157,255,217,0.6)] outline-transparent hover:outline-[#04FDE0] outline-[2px] transition-all duration-[0.5s]"
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
