import { api } from "./api";
import type { AlocacaoViewDTO } from "../models/alocacao";

export async function getAlocacoes(): Promise<AlocacaoViewDTO[]> {
    const response = await api.get<AlocacaoViewDTO[]>("/alocacoes")
    return response.data;
}

export async function getAlocacoesPorProjeto(projetoId: number): Promise<AlocacaoViewDTO[]> {
    const response = await api.get<AlocacaoViewDTO[]>(`/alocacoes/projeto/${projetoId}`)
    return response.data;
}