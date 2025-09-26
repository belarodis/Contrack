type AlocacaoProps = {
  horasSemana: number;
  nomePessoa: string;
  tipoPerfil: string;
};

function Alocacao({ nomePessoa, tipoPerfil, horasSemana }: AlocacaoProps) {
  return (
    <div className="flex flex-col bg-[#0D3445] w-full rounded-[2vh] text-white py-[15px] px-[20px] font-semibold h-fit">
      <div className="flex items-center gap-[10px]">
        <img
          src="/alocacao-icon.svg"
          alt="plus"
          className="flex w-auto h-[16px]"
        />
        <h3 className="text-[20px] items-center font-bold ">{nomePessoa}</h3>
      </div>
      <p className="text-[14px] font-medium mb-[2px] truncate">
        {`${horasSemana}h/semana`}
      </p>
      <p className="text-[14px] font-light truncate">
        {tipoPerfil.toUpperCase()}
      </p>
    </div>
  );
}

export default Alocacao;
