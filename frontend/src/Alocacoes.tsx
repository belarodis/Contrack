import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Alocacao from "./Alocacao";
import { useEffect, useState } from "react";
import Overlay from "./components/modals/Overlay.tsx";
import CriarAlocacao from "./components/modals/CriarAlocacao.tsx";
import { getAlocacoesPorProjeto } from "./services/alocacoes.service.ts";
import { ToastContainer } from "react-toastify";

type AlocacoesProps = {
  selectedId: number;
};

function Alocacoes({ selectedId }: AlocacoesProps) {
  const [alocacoes, setAlocacoes] = useState<any[]>([]);
  const [openModal, setOpenModal] = useState(false);

  // carregar ao montar ou quando selectedId mudar
  useEffect(() => {
    async function carregar() {
      try {
        const data = await getAlocacoesPorProjeto(selectedId);
        setAlocacoes(data);
      } catch (e) {
        console.error("Erro ao carregar pessoas", e);
      }
    }

    carregar();
  }, [selectedId]);

  // fechar modal + recarregar lista
  async function handleClose() {
    setOpenModal(false);
    try {
      const data = await getAlocacoesPorProjeto(selectedId);
      setAlocacoes(data);
    } catch (e) {
      console.error("Erro ao recarregar pessoas", e);
    }
  }

  return (
    <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[48px] pt-[30px] basis-8/12 min-h-0">
      <div className="flex flex-row justify-between w-full h-fit items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Alocações</h1>
        <ButtonPlus onClick={() => setOpenModal(true)} />
      </div>

      {/* grid com scroll */}
      <div className="grid grid-cols-2 gap-[20px] pt-[15px] pb-[20px] overflow-y-auto">
        {alocacoes.map((a) => (
          <Alocacao
            key={a.id}
            nomePessoa={a.nomePessoa}
            tipoPerfil={a.tipoPerfil}
            horasSemana={a.horasSemana}
          />
        ))}
      </div>

      {openModal && (
        <Overlay onClose={() => setOpenModal(false)}>
          <ToastContainer
            position="top-center"
            autoClose={4000}
            style={{ zIndex: 9999 }}
          />
          <CriarAlocacao onClose={handleClose} projetoId={selectedId} />
        </Overlay>
      )}
    </div>
  );
}

export default Alocacoes;
