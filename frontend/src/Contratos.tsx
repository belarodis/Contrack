import ButtonPlus from "./components/buttons/ButtonPlus.tsx";
import Contrato from "./Contrato";
import { useEffect, useState } from "react";
import Overlay from "./components/modals/Overlay.tsx";
import CriarContrato from "./components/modals/CriarContrato.tsx";
import { getContratos } from "./services/contratos.service.ts";
import { ToastContainer } from "react-toastify";

function Contratos() {
  const [contratos, setContratos] = useState<any[]>([]);

  const [openModal, setOpenModal] = useState(false);

  // função pra carregar as pessoas
  async function carregarContratos() {
    try {
      const data = await getContratos();
      setContratos(data);
    } catch (e) {
      console.error("Erro ao carregar pessoas", e);
    }
  }

  // carregar ao montar o componente
  useEffect(() => {
    carregarContratos();
  }, []);

  // fechar modal + recarregar lista
  function handleClose() {
    setOpenModal(false);
    carregarContratos();
  }

  return (
    <div className="bg-[#0A2439] flex flex-col rounded-[25px] px-[48px] pt-[30px] basis-2/3 min-h-0">
      <div className="flex flex-row justify-between w-full h-fit items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Contratos</h1>
        <ButtonPlus onClick={() => setOpenModal(true)} />
      </div>

      {/* grid com scroll */}
      <div className="grid grid-cols-2 gap-[1vw] pt-[15px] h-full min-h-0 overflow-y-auto px-[10px]">
        {contratos.map((c) => (
          <div key={c.id} className="w-full h-full flex">
            <Contrato
              pessoaNome={c.nomePessoa}
              dataInicio={c.dataInicio}
              dataFim={c.dataFim}
              horasSemana={c.horasSemana}
              salarioHora={c.salarioHora}
              ativo={c.ativo}
            />
          </div>
        ))}
      </div>
      {openModal && (
        <Overlay onClose={() => setOpenModal(false)}>
          <ToastContainer
            position="top-center"
            autoClose={4000}
            style={{ zIndex: 9999 }}
          />
          <CriarContrato onClose={handleClose} />
        </Overlay>
      )}
    </div>
  );
}

export default Contratos;
