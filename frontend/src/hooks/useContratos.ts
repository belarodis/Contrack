import { useEffect, useState } from "react";
import { getContratos } from "../services/contratos.service.ts";
import type { ContratoViewDTO } from "../models/contrato.ts";

export function useContratos() {
  const [data, setData] = useState<ContratoViewDTO[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const contratos = await getContratos();
        setData(contratos);
      } catch (e: any) {console.log("Erro na hook useContratos())")}})();
  }, []);

  return data;
}
