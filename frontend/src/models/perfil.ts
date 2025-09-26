export type TipoPerfil = "gerente" |"dev" | "qa" | "security"

export type PerfilViewDTO = {
  id: number;
  tipo: TipoPerfil;
};
