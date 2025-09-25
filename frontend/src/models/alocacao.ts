export type AlocacaoCreateDTO = {
  projetoId: number;
  pessoaId: number;
  horasSemana: number;
};

export type AlocacaoViewDTO = {
  id: number;
  projetoId: number;
  pessoaId: number;
  horasSemana: number;
};
