import ButtonPlus from "./ButtonPlus";
import Projeto from "./Projeto";

const projetos = [
  { id: 1, nome: "A" },
  { id: 2, nome: "B" },
  { id: 3, nome: "C" },
  { id: 4, nome: "D" },
  { id: 5, nome: "E" },
];

// interface ProjetosProps {
//   onSelect: (id: number) => void;
// }

function Projetos() {
  return (
    <div className="bg-[#0A2439] flex flex-col flex-1 h-full rounded-[25px] px-[48px] pt-[30px]">
      <div className="flex flex-row justify-between items-center">
        <div className="flex flex-col">
          <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Projetos</h1>
          <p className="text-[#9DA7B0]">
            Selecione um projeto para mais informações
          </p>
        </div>
        <ButtonPlus />
      </div>
      <div className="grid grid-cols-2 gap-[1vw] pt-[15px]">
        {projetos.map((p) => (
          <div
            key={p.id}
            className="w-full h-full flex"
          >
            <Projeto />
          </div>
        ))}
      </div>
    </div>
  );
}

export default Projetos;
