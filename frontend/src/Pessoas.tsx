import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Pessoa from "./Pessoa";
import {useState} from "react";
import Overlay from "./components/modals/Overlay.tsx";
import {CriarPessoa} from "./components/modals/CriarPessoa.tsx";

const pessoas = [
  { id: 1, nome: "A" },
  { id: 2, nome: "B" },
  { id: 3, nome: "C" },
  { id: 4, nome: "D" },
  { id: 5, nome: "E" },
];

function Pessoas() {
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
            <Pessoa />
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
