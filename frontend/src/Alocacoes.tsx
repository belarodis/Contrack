import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Alocacao from "./Alocacao";
import { useEffect, useState } from "react";
import Overlay from "./components/modals/Overlay.tsx";
import CriarAlocacao from "./components/modals/CriarAlocacao.tsx";
import { getAlocacoesPorProjeto } from "./services/alocacoes.service.ts";

type AlocacoesProps = {
  selectedId: number;
};

function Alocacoes({ selectedId }: AlocacoesProps) {
  const [alocacoes, setAlocacoes] = useState<any[]>([]);

  const [openModal, setOpenModal] = useState(false);

  // função pra carregar as pessoas
  async function carregarPessoas() {
    try {
      const data = await getAlocacoesPorProjeto(selectedId);
      setAlocacoes(data);
    } catch (e) {
      console.error("Erro ao carregar pessoas", e);
    }
  }

  // carregar ao montar o componente
  useEffect(() => {
    carregarPessoas();
  }, []);

  // fechar modal + recarregar lista
  function handleClose() {
    setOpenModal(false);
    carregarPessoas();
  }

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
            onClose={handleClose}
            projetoId={selectedId} // 👈 passa o valor aqui
          />
        </Overlay>
      )}
    </div>
  );
}

export default Alocacoes;
