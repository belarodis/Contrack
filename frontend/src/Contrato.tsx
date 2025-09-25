type ContratoProps = {
  nome: string;
  dataInicio: string;
  dataFim: string;
  horasSemana: number;
  salarioHora: number;
  status: string;
};

function Contrato({nome, dataInicio, dataFim, horasSemana, salarioHora, status}: ContratoProps) {
  return (
    <div className="flex flex-col bg-[#0D3445] h-[18vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold">
      <h3 className="text-[18px] font-bold">{nome}</h3>
      <div className="flex flex-row gap-[15px]">
        <p className="text-[13px]">
          <span className="text-[#C2CCD0]">Inicio: </span>{dataInicio}
          {}
        </p>
        <p className="text-[13px]">
          <span className="text-[#C2CCD0]">Fim: </span>{dataFim}
        </p>
      </div>
      <p className="text-[13px]">{horasSemana}h/semana</p>
      <p className="text-[13px]">R${salarioHora}/h</p>
    <div className="w-[50px] h-[10px] rounded-[100px] bg-[#6ECD84]"></div>
    </div>
  );
}

export default Contrato;
