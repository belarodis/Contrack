import { useEffect, useState } from "react";
import { getCustoProjeto } from "../services/projeto.service";

export function useCustoProjeto(id: number | null) {
    const [custo, setCusto] = useState<number | null>(null);

    useEffect(() => {
        if (id === null) return;

        (async () => {
            try {
                const valor = await getCustoProjeto(id);
                setCusto(valor);
            } catch (e) {
                console.error("Erro na hook useCustoProjeto()", e);
            }
        })();
    }, [id]);

    return custo;
}