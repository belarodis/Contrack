type PessoaProps = {
  nome: string;
  disponivel: boolean;
};

function Pessoa({nome, disponivel}:PessoaProps) {
  return (
    <div className="flex flex-col bg-[#0D3445] h-[11vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold">
      <h3 className="text-[18px] font-bold">{nome}</h3>
      <div className="flex flex-row gap-[15px]"></div>
      <div className={`w-[50px] h-[10px] rounded-[100px] ${disponivel ? "bg-[#6ECD84]" : "bg-[#3D5D6A]"}`}></div>
    </div>
  );
}

export default Pessoa;
