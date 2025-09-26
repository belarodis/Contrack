// hooks/useCustoPeriodo.ts
import { useEffect, useState } from "react";
import { getCustoPeriodo } from "../services/projeto.service";

export function useCustoPeriodo(
    projetoId: number | null,
    inicio: string,
    fim: string
) {
    const [valor, setValor] = useState<number | null>(null);

    useEffect(() => {
        // só busca se tudo estiver ok
        if (!projetoId || !inicio || !fim) {
            setValor(null);
            return;
        }
        if (fim < inicio) {
            setValor(null);
            return;
        }

        (async () => {
            try {
                const v = await getCustoPeriodo(projetoId, inicio, fim);
                setValor(v);
            } catch (e) {
                console.error("Erro em useCustoPeriodo()", e);
                setValor(null);
            }
        })();
    }, [projetoId, inicio, fim]);

    return valor;
}