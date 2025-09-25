import { api } from "./api";
import type { PerfilViewDTO } from "../models/perfil.ts";

export async function getPerfis(): Promise<PerfilViewDTO[]> {
    const response = await api.get<PerfilViewDTO[]>("/Perfis")
    return response.data;
}