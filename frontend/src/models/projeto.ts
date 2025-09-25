export type ProjetoCreateDTO = {
  nome: string;
  dataInicio: string; // YYYY-MM-DD
  dataFim: string;    // YYYY-MM-DD
  descricao: string;
};

export type ProjetoStatus = "Finalizado" | "Em espera" | "Incompleto" | "Ativo";

export type ProjetoViewDTO = {
  id: number;
  nome: string;
  dataInicio: string;
  dataFim: string;
  descricao: string;
  status: ProjetoStatus;
};
