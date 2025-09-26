type PessoaProps = {
  nome: string;
  disponivel: boolean;
};

function Pessoa({ nome, disponivel }: PessoaProps) {
  return (
    <div className="flex flex-col justify-between bg-[#0D3445] h-[12vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] font-semibold">
      <div className="flex flex-col gap-[5px]">
        <div className="flex flex-row gap-[10px] items-center justify-start">
          <img
            src="/pessoa-icon.svg"
            alt="plus"
            className="flex w-auto h-[18px]"
          />
          <h3 className="text-[18px] font-bold">{nome}</h3>
        </div>
        <div className="flex flex-row gap-[10px]"></div>
      </div>

      <div
        className={`w-[50px] h-[10px] rounded-[100px] ${
          disponivel ? "bg-[#6ECD84]" : "bg-[#3D5D6A]"
        }`}
      />
    </div>
  );
}

export default Pessoa;
