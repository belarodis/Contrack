import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Pessoa from "./Pessoa";
import { useState, useEffect } from "react";
import Overlay from "./components/modals/Overlay.tsx";
import { CriarPessoa } from "./components/modals/CriarPessoa.tsx";
import { getPessoas } from "./services/pessoas.service"; // 👈 usar o service diretamente

function Pessoas() {
  const [pessoas, setPessoas] = useState<any[]>([]);
  const [openModal, setOpenModal] = useState(false);

  // função pra carregar as pessoas
  async function carregarPessoas() {
    try {
      const data = await getPessoas();
      setPessoas(data);
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
    <div className="bg-[#0A2439] flex flex-col flex-1 h-[35%] rounded-[25px] px-[48px] pt-[30px]">
      <div className="flex flex-row justify-between items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Pessoas</h1>
        <ButtonPlus onClick={() => setOpenModal(true)} />
      </div>

      <div className="grid grid-cols-3 gap-[1vw] pt-[15px] h-full overflow-y-auto pr-[20px]">
        {pessoas.map((p) => (
          <div key={p.id} className="w-full h-full flex">
            <Pessoa nome={p.nome} disponivel={p.disponivel} />
          </div>
        ))}
      </div>

      {openModal && (
        <Overlay onClose={() => setOpenModal(false)}>
          <CriarPessoa onClose={handleClose} />
        </Overlay>
      )}
    </div>
  );
}

export default Pessoas;
