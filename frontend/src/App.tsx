import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import Alocacoes from "./Alocacoes";
import Contratos from "./Contratos";
import Pessoas from "./Pessoas";
import Projetos from "./Projetos";
import Custos from "./Custos";

function App() {
  const [selectedProjeto, setSelectedProjeto] = useState<null | number>(null);

  return (
    <div className="flex h-full w-full gap-[38px]">
      {/* Coluna esquerda */}
      <div className="w-1/2 min-w-0">
        {/* agora Projetos recebe uma função de callback */}
        <Projetos onSelectProjeto={setSelectedProjeto} />
      </div>

      {/* Coluna direita com transição */}
      <AnimatePresence mode="wait" initial={false}>
        {selectedProjeto === null ? (
          <motion.div
            key="default"
            initial={{ opacity: 0, x: 30 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: -30 }}
            transition={{ duration: 0.35 }}
            layout
            className="w-1/2 min-w-0 flex flex-col gap-[38px] overflow-hidden"
          >
            <Pessoas />
            <Contratos />
          </motion.div>
        ) : (
          <motion.div
            key="detalhes"
            initial={{ opacity: 0, x: -30 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: 30 }}
            transition={{ duration: 0.35 }}
            layout
            className="w-1/2 min-w-0 flex flex-col gap-[38px] overflow-hidden"
          >
            <Alocacoes />
            <Custos />
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}

export default App;
