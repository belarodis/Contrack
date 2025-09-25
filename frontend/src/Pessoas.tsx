import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Pessoa from "./Pessoa";
import {useState} from "react";
import Overlay from "./components/modals/Overlay.tsx";
import {CriarPessoa} from "./components/modals/CriarPessoa.tsx";
import { usePessoas } from "./hooks/usePessoas";

function Pessoas() {
    const pessoas = usePessoas();
    const [openModal, setOpenModal] = useState(false);

  return (
    <div className="bg-[#0A2439] flex flex-col flex-1 rounded-[25px] px-[48px] pt-[30px]">
      <div className="flex flex-row justify-between items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Pessoas</h1>
        <ButtonPlus onClick={() => setOpenModal(true)}/>
      </div>

      <div className="grid grid-cols-3 gap-[1vw] pt-[15px]">
        {pessoas.map((p) => (
          <div key={p.id} className="w-full h-full flex">
            <Pessoa nome={p.nome}/>
          </div>
        ))}
      </div>
        {openModal && (
            <Overlay onClose={() => setOpenModal(false)}>
                <CriarPessoa onClose={() => setOpenModal(false)} />
            </Overlay>
        )}
    </div>
  );
}

export default Pessoas;
