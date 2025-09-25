import { useEffect, useState } from "react";
import { getPessoas } from "../services/pessoas.service.ts";
import type { PessoaViewDTO } from "../models/pessoa.ts";

export function usePessoas() {
  const [data, setData] = useState<PessoaViewDTO[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const pessoas = await getPessoas();
        setData(pessoas);
      } catch (e: any) {console.log("Erro na hook usePessoas()")}})();
  }, []);

  return data;
}
