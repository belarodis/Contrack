import { useEffect, useState } from "react";
import { getProjetos } from "../services/projeto.service";
import type { ProjetoViewDTO } from "../models/projeto";

export function useProjetos() {
  const [data, setData] = useState<ProjetoViewDTO[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const projetos = await getProjetos();
        setData(projetos);
      } catch (e: any) {console.log("Erro na hook useProjetos()");}})();
  }, []);

  return data;
}
