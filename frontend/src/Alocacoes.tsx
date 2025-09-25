import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Alocacao from "./Alocacao";
import {useState} from "react";
import Overlay from "./components/modals/Overlay.tsx";
import CriarAlocacao from "./components/modals/CriarAlocacao.tsx";

function Alocacoes() {
  const alocacoes = [
    { id: 1, nome: "A" },
    { id: 2, nome: "B" },
    { id: 3, nome: "C" },
    { id: 4, nome: "D" },
    { id: 5, nome: "E" },
  ];

    const [openModal, setOpenModal] = useState(false);

  return (
    <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[48px] pt-[30px] basis-2/3 min-h-0">
      <div className="flex flex-row justify-between w-full h-fit items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Alocacoes</h1>
          <ButtonPlus onClick={() => setOpenModal(true)}/>
      </div>

      {/* grid com scroll */}
      <div className="grid grid-cols-2 gap-[1vw] pt-[15px] h-full min-h-0 overflow-y-auto px-[10px]">
        {alocacoes.map((p) => (
          <div key={p.id} className="w-full h-full flex">
            <Alocacao />
          </div>
        ))}
      </div>
        {openModal && (
            <Overlay onClose={() => setOpenModal(false)}>
                <CriarAlocacao onClose={() => setOpenModal(false)} />
            </Overlay>
        )}
    </div>
  );
}

export default Alocacoes;
