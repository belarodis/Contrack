import { useEffect, useState } from "react";
import { getPerfis } from "../services/perfis.service.ts";
import type { PerfilViewDTO } from "../models/perfil.ts";

export function usePerfis() {
  const [data, setData] = useState<PerfilViewDTO[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const perfis = await getPerfis();
        setData(perfis);
      } catch (e: any) {console.log("Erro na hook usePerfis())")}})();
  }, []);

  return data;
}
