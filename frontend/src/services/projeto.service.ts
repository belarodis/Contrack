import { api } from "./api"; // api é o axios.create() que configuramos
import type { ProjetoCreateDTO, ProjetoViewDTO } from "../models/projeto.ts";

export async function getProjetos(): Promise<ProjetoViewDTO[]> {
  const response = await api.get<ProjetoViewDTO[]>("/projetos");
  return response.data;
}

export async function createProjetos(dto: ProjetoCreateDTO): Promise<ProjetoViewDTO> {
  const res = await api.post<ProjetoViewDTO>("/projetos", dto);
  console.log(res.data.nome)
  return res.data;
}


export async function getProjeto(id: number): Promise<ProjetoViewDTO> {
    const res = await api.get<ProjetoViewDTO>(`/projetos/${id}`);
    return res.data;
}

export async function getCustoProjeto(id: number): Promise<number> {
    const res = await api.get<number>(`/projetos/${id}/custo-total`);
    console.log(res.data);
    return res.data;
}


export async function getCustoPeriodo(id: number, dataInicio: string, dataFim: string): Promise<number> {
    const res = await api.get<number>(`/projetos/${id}/custo-periodo`, {
        params: { dataInicio, dataFim },
    });
    return res.data;
}


