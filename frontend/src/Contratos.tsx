import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Contrato from "./Contrato";
import { useContratos } from "./hooks/useContratos";
import { usePessoas } from "./hooks/usePessoas";

function Contratos() {
  const contratos = useContratos();
  const pessoas = usePessoas();

  return (
    <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[48px] pt-[30px] basis-2/3 min-h-0">
      <div className="flex flex-row justify-between w-full h-fit items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Contratos</h1>
        <ButtonPlus />
      </div>

      {/* grid com scroll */}
      <div className="grid grid-cols-2 gap-[1vw] pt-[15px] h-full min-h-0 overflow-y-auto px-[10px]">
        {contratos.map((c) => (
          <div key={c.id} className="w-full h-full flex">
            <Contrato
              nome={
                pessoas.find((p) => p.id === c.pessoaId)?.nome ?? "Desconhecido"
              }
              dataInicio={c.dataInicio}
              dataFim={c.dataFim}
              horasSemana={c.horasSemana}
              salarioHora={c.salarioHora}
              status={c.status}
            />
          </div>
        ))}
      </div>
    </div>
  );
}

export default Contratos;
