export type AlocacaoCreateDTO = {
  projetoId: number;
  pessoaId: number;
  horasSemana: number;
};

export type AlocacaoViewDTO = {
  id: number;
  horasSemana: number;
  pessoaId: number;
  nomePessoa: string;
  projetoId: number;
  nomeProjeto: string;
  perfilId: number;
  tipoPerfil: string;
};
