// hooks/useProjeto.ts
import { useEffect, useState } from "react";
import { getProjeto } from "../services/projeto.service";
import type { ProjetoViewDTO } from "../models/projeto";

export function useProjeto(id: number | null) {
    const [projeto, setProjeto] = useState<ProjetoViewDTO | null>(null);

    useEffect(() => {
        if (!id) { setProjeto(null); return; }
        (async () => {
            try {
                const p = await getProjeto(id);
                setProjeto(p);
            } catch (e) {
                console.error("Erro ao carregar projeto", e);
                setProjeto(null);
            }
        })();
    }, [id]);

    return projeto;
}