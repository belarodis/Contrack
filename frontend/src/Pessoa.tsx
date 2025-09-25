type PessoaProps = { nome: string };

export default function Pessoa({ nome }: PessoaProps) {
    return (
        <div className="flex flex-col bg-[#0D3445] h-[11vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold">
            <h3 className="text-[16px] font-bold truncate">{nome}</h3>
            <div className="flex flex-row gap-[15px]" />
            <div className="w-[50px] h-[10px] rounded-[100px] bg-[#6ECD84]" />
        </div>
    );
}