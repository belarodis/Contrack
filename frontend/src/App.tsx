import Alocacao from "./Alocacao";
import Alocacoes from "./Alocacoes";
import Contratos from "./Contratos";
import Pessoas from "./Pessoas";
import Projetos from "./Projetos";

function App() {
  return (
    <div className="flex h-full w-full gap-[38px]">
      <div className="w-1/2 min-w-0">
        <Projetos />
      </div>
      {/* Esse aparece quando nao tem projeto selecionado */}
      <div className="w-1/2 min-w-0 flex flex-col gap-[38px]">
        <Pessoas />
        <Contratos />
      </div>
      {/* Esse aparece só quando eu clickar em um projeto */}
      {/* <div className="w-1/2 min-w-0 flex flex-col gap-[38px]">
        <Alocacoes />
        <Custos />
      </div> */}
    </div>
  );
}

export default App;
