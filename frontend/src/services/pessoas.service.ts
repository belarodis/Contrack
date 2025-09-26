import { api } from "./api"; // api é o axios.create() que configuramos
import type { PessoaCreateDTO, PessoaViewDTO } from "../models/pessoa.ts";

export async function getPessoas(): Promise<PessoaViewDTO[]> {
  const response = await api.get<PessoaViewDTO[]>("/pessoas");
  return response.data;
}

export async function createPessoa(dto: PessoaCreateDTO): Promise<PessoaViewDTO> {
  const res = await api.post<PessoaViewDTO>("/pessoas", dto);
  console.log(res.data.nome)
  return res.data;
}
