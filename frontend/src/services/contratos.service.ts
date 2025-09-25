import { api } from "./api";
import type { ContratoViewDTO } from "../models/contrato";

export async function getContratos(): Promise<ContratoViewDTO[]> {
  const response = await api.get<ContratoViewDTO[]>("/contratos");
  return response.data;
}
