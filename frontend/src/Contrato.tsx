type ContratoProps = {
  pessoaNome: string;
  dataInicio: string;
  dataFim: string;
  horasSemana: number;
  salarioHora: number;
  ativo: boolean;
};

function Contrato({
  pessoaNome,
  dataInicio,
  dataFim,
  horasSemana,
  salarioHora,
  ativo,
}: ContratoProps) {
  return (
    <div className="flex flex-col justify-between bg-[#0D3445] h-[18vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] font-semibold">
      {/* Bloco de conteúdo */}
      <div className="flex flex-col gap-[5px]">
        <div className="flex items-center gap-[10px]">
          <img src="/contrato-icon.svg" alt="plus" className="flex" />
          <h3 className="text-[18px] font-bold">{pessoaNome}</h3>
        </div>
        <div className="flex flex-row gap-[15px]">
          <p className="text-[13px]">
            <span className="text-[#C2CCD0]">Início: </span>
            {dataInicio}
          </p>
          <p className="text-[13px]">
            <span className="text-[#C2CCD0]">Fim: </span>
            {dataFim}
          </p>
        </div>
        <p className="text-[13px]">{horasSemana}h/semana</p>
        <p className="text-[13px]">R${salarioHora}/h</p>
      </div>

      {/* Status fixo embaixo */}
      <div
        className={`w-[50px] h-[10px] rounded-[100px] ${
          ativo ? "bg-[#6ECD84]" : "bg-[#3D5D6A]"
        }`}
      />
    </div>
  );
}

export default Contrato;
