import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Projeto from "./Projeto";
import { useEffect, useState } from "react";
import Overlay from "./components/modals/Overlay.tsx";
import CriarProjeto from "./components/modals/CriarProjeto.tsx";
import { getProjetos } from "./services/projeto.service.ts";
import { ToastContainer } from "react-toastify";

interface ProjetosProps {
  onSelectProjeto: (id: number | null) => void;
  selectedId: number | null;
}

function Projetos({ onSelectProjeto, selectedId }: ProjetosProps) {
  const [openModal, setOpenModal] = useState(false);
  const [projetos, setProjetos] = useState<any[]>([]);

  // fechar modal + recarregar lista
  function handleClose() {
    setOpenModal(false);
    carregarProjetos();
  }

  // função pra carregar as pessoas
  async function carregarProjetos() {
    try {
      const data = await getProjetos();
      setProjetos(data);
    } catch (e) {
      console.error("Erro ao carregar pessoas", e);
    }
  }

  // carregar ao montar o componente
  useEffect(() => {
    carregarProjetos();
  }, []);

  return (
    <div className="bg-[#0A2439] flex flex-col flex-1 h-full rounded-[25px] px-[48px] pt-[30px] ">
      <div className="flex flex-row justify-between items-center ">
        <div className="flex flex-col">
          <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Projetos</h1>
          <p className="text-[#9DA7B0]">
            Selecione um projeto para mais informações
          </p>
        </div>
        <ButtonPlus onClick={() => setOpenModal(true)} />
      </div>
      <div className="grid grid-cols-2 gap-[1vw] pl-[5px] pb-[20px] pt-[15px] pr-[20px] overflow-y-auto ">
        {projetos.map((p) => (
          <Projeto
            key={p.id}
            id={p.id}
            nome={p.nome}
            descricao={p.descricao}
            dataInicio={p.dataInicio}
            dataFim={p.dataFim}
            onSelect={onSelectProjeto}
            selectedId={selectedId}
            status={p.status}
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
          <CriarProjeto onClose={handleClose} />
        </Overlay>
      )}
    </div>
  );
}

export default Projetos;
