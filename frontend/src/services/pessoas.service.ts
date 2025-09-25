import { api } from "./api"; // api é o axios.create() que configuramos
import type { PessoaViewDTO } from "../models/pessoa.ts";

export async function getPessoas(): Promise<PessoaViewDTO[]> {
  const response = await api.get<PessoaViewDTO[]>("/pessoas");
  return response.data;
}
