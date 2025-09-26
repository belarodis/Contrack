import { api } from "./api";
import type { ContratoCreateDTO, ContratoViewDTO } from "../models/contrato";

export async function getContratos(): Promise<ContratoViewDTO[]> {
  const response = await api.get<ContratoViewDTO[]>("/contratos");
  return response.data;
}

export async function createContrato(dto: ContratoCreateDTO): Promise<ContratoViewDTO> {
  const res = await api.post<ContratoViewDTO>("/contratos", dto);
  return res.data;
}