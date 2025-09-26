import { useEffect, useState } from "react";
import {getCustoPeriodo} from "../services/projeto.service";

export function useCustoPeriodo(id: number | null) {
    const [custo, setCusto] = useState<number | null>(null);

    useEffect(() => {
        if (id === null) return;

        (async () => {
            try {
                const valor = await getCustoPeriodo(id);
                setCusto(valor);
            } catch (e) {
                console.error("Erro na hook useCustoPeriodo()", e);
            }
        })();
    }, [id]);

    return custo;
}