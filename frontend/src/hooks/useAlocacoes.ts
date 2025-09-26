import { useEffect, useState } from "react";
import { getAlocacoes, getAlocacoesPorProjeto } from "../services/alocacoes.service.ts";
import type { AlocacaoViewDTO } from "../models/alocacao.ts";

export function useAlocacoes() {
  const [data, setData] = useState<AlocacaoViewDTO[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const alocacoes = await getAlocacoes();
        setData(alocacoes);
      } catch (e: any) {console.log("Erro na hook useAlocacoes())")}})();
  }, []);

  return data;
}

export function useAlocacoesPorProjeto(selectedId: number) {
  const [data, setData] = useState<AlocacaoViewDTO[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const alocacoes = await getAlocacoesPorProjeto(selectedId);
        setData(alocacoes);
      } catch (e: any) {console.log("Erro na hook useAlocacoesPorProjeto())")}})();
  }, []);

  return data;
}
