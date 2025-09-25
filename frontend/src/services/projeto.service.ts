import { api } from "./api"; // api é o axios.create() que configuramos
import type { ProjetoViewDTO } from "../models/projeto.ts";

export async function getProjetos(): Promise<ProjetoViewDTO[]> {
  const response = await api.get<ProjetoViewDTO[]>("/projetos");
  return response.data;
}
