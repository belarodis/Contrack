type AlocacaoProps = {
    horasSemana: number;
    nomePessoa: string;
    tipoPerfil: string;
};

function Alocacao({ nomePessoa, tipoPerfil, horasSemana }: AlocacaoProps) {
    return (
        <div className="flex flex-col bg-[#0D3445] w-full rounded-[2vh] text-white py-[15px] px-[20px] font-semibold h-fit">
            <h3 className="text-[22px] font-bold mb-[6px] truncate whitespace-nowrap">
                {nomePessoa}
            </h3>
            <p className="text-[14px] font-medium mb-[2px] truncate">
                {`${horasSemana}h/semana`}
            </p>
            <p className="text-[14px] font-light truncate">{tipoPerfil.toUpperCase()}</p>
        </div>
    );
}

export default Alocacao;