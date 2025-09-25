export type ContratoCreateDTO = {
  dataInicio: string; // ISO
  dataFim: string; // ISO
  horasSemana: number;
  salarioHora: number;
  pessoaId: number;
};

export type ContratoViewDTO = {
  id: number;
  dataInicio: string;
  dataFim: string;
  horasSemana: number;
  salarioHora: number;
  pessoaId: number;
  status: string;
};
