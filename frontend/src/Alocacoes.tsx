import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Alocacao from "./Alocacao";
import { useState } from "react";
import Overlay from "./components/modals/Overlay.tsx";
import CriarAlocacao from "./components/modals/CriarAlocacao.tsx";
import { useAlocacoesPorProjeto } from "./hooks/useAlocacoes.ts";

type AlocacoesProps = {
  selectedId: number;
};

function Alocacoes({ selectedId }: AlocacoesProps) {
  const alocacoes = useAlocacoesPorProjeto(selectedId);

  const [openModal, setOpenModal] = useState(false);

  return (
    <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[48px] pt-[30px] basis-2/3 min-h-0">
      <div className="flex flex-row justify-between w-full h-fit items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Alocacoes</h1>
        <ButtonPlus onClick={() => setOpenModal(true)} />
      </div>

      {/* grid com scroll */}
      <div className="grid grid-cols-2 gap-[1vw] pt-[15px] h-full min-h-0 overflow-y-auto px-[10px]">
        {alocacoes.map((a) => (
          <div key={a.id} className="w-full h-full flex">
            <Alocacao
              nomePessoa={a.nomePessoa}
              tipoPerfil={a.tipoPerfil}
              horasSemana={a.horasSemana}
            />
          </div>
        ))}
      </div>
{openModal && (
  <Overlay onClose={() => setOpenModal(false)}>
    <CriarAlocacao 
      onClose={() => setOpenModal(false)} 
      projetoId={selectedId}   // 👈 passa o valor aqui
    />
  </Overlay>
)}

    </div>
  );
}

export default Alocacoes;
