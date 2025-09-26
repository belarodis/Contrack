export type PessoaCreateDTO = {
  nome: string;
  cargo: string;
  salarioHora: number;
};

export type PessoaViewDTO = {
  id: number;
  nome: string;
  disponivel: boolean;
};
