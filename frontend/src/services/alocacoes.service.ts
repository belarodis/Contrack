import { api } from "./api";
import type { AlocacaoViewDTO } from "../models/alocacao";

export async function getAlocacoes(): Promise<AlocacaoViewDTO[]> {
    const response = await api.get<AlocacaoViewDTO[]>("/alocacao")
    return response.data;
}