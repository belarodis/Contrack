type AlocacaoProps = {
  horasSemana: number;
  nomePessoa: string;
  tipoPerfil: string;
};

function Alocacao({ nomePessoa, tipoPerfil, horasSemana }: AlocacaoProps) {
  return (
    <div className="flex flex-col bg-[#0D3445] h-fit w-full rounded-[2vh] text-white py-[15px] px-[20px] font-semibold">
      <div className="flex items-center gap-[10px]">
        <img
          src="/alocacao-icon.svg"
          alt="plus"
          className="flex w-auto h-[18px]"
        />
        <h3 className="text-[22px] font-bold">{nomePessoa}</h3>
      </div>
      <p className="text-[14px] font-medium pb-[3px]">{`${horasSemana}h/semana`}</p>
      <p className="text-[14px] font-light">{tipoPerfil.toUpperCase()}</p>
    </div>
  );
}

export default Alocacao;
