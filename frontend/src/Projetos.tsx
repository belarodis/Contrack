import { useProjetos } from "./hooks/useProjetos";
import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Projeto from "./Projeto";
import {useState} from "react";
import Overlay from "./components/modals/Overlay.tsx";
import CriarProjeto from "./components/modals/CriarProjeto.tsx";

interface ProjetosProps {
  onSelectProjeto: (id: number | null) => void;
  selectedId: number | null;
}

function Projetos({ onSelectProjeto, selectedId }: ProjetosProps) {
    const [openModal, setOpenModal] = useState(false);
    const projetos = useProjetos();

  return (
    <div className="bg-[#0A2439] flex flex-col flex-1 h-full rounded-[25px] px-[48px] pt-[30px] ">
      <div className="flex flex-row justify-between items-center ">
        <div className="flex flex-col">
          <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Projetos</h1>
          <p className="text-[#9DA7B0]">
            Selecione um projeto para mais informações
          </p>
        </div>
          <ButtonPlus onClick={() => setOpenModal(true)}/>
      </div>
      <div className="grid grid-cols-2 gap-[1vw] pt-[15px] overflow-y-auto pr-[20px]">
        {projetos.map((p) => (
          <Projeto
            key={p.id}
            id={p.id}
            nome={p.nome}
            onSelect={onSelectProjeto}
            selectedId={selectedId}
            status={p.status}
          />
        ))}
      </div>
        {openModal && (
            <Overlay onClose={() => setOpenModal(false)}>
                <CriarProjeto onClose={() => setOpenModal(false)} />
            </Overlay>
        )}
    </div>

  );
}

export default Projetos;
