import ButtonPlus from "./ButtonPlus";
import Pessoa from "./Pessoa";
import { usePessoas } from "./hooks/usePessoas";

function Pessoas() {
  const pessoas = usePessoas();

  return (
    <div className="bg-[#0A2439] flex flex-col flex-1 rounded-[25px] px-[48px] pt-[30px]">
      <div className="flex flex-row justify-between items-center">
        <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Pessoas</h1>
        <ButtonPlus />
      </div>

      <div className="grid grid-cols-3 gap-[1vw] pt-[15px]">
        {pessoas.map((p) => (
          <div key={p.id} className="w-full h-full flex">
            <Pessoa nome={p.nome}/>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Pessoas;
