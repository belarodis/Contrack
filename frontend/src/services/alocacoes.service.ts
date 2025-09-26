import { api } from "./api";
import type { AlocacaoCreateDTO, AlocacaoViewDTO } from "../models/alocacao";

export async function getAlocacoes(): Promise<AlocacaoViewDTO[]> {
    const response = await api.get<AlocacaoViewDTO[]>("/alocacoes")
    return response.data;
}

export async function getAlocacoesPorProjeto(projetoId: number): Promise<AlocacaoViewDTO[]> {
    const response = await api.get<AlocacaoViewDTO[]>(`/alocacoes/projetos/${projetoId}/alocacoes`)
    return response.data;
}

export async function createAlocacao(dto: AlocacaoCreateDTO): Promise<AlocacaoCreateDTO> {
  const res = await api.post<AlocacaoCreateDTO>("/alocacoes", dto);
  return res.data;
}