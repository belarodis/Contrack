export type AlocacaoCreateDTO = {
  horasSemana: number;
  pessoaId: number;
  projetoId: number;
  perfilId: number;
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
