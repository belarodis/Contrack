type AlocacaoProps = {
  horasSemana: number;
  nomePessoa: string;
  tipoPerfil: string;
};

function Alocacao({nomePessoa, tipoPerfil, horasSemana} : AlocacaoProps) {
  return (
    <div className="flex flex-col bg-[#0D3445] h-[18vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold">
      <h3 className="text-[18px] font-bold">{nomePessoa}</h3>
      <div className="flex flex-row gap-[15px]"></div>
      <p className="text-[13px]">{tipoPerfil}</p>
      <p className="text-[13px]">{`${horasSemana}h/semana`}</p>
    </div>
  );
}

export default Alocacao;
