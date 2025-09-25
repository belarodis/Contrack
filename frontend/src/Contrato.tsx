function Contrato(){
    return(
        <div className="flex flex-col bg-[#0D3445] h-[18vh] w-full rounded-[2vh] text-white py-[12px] px-[16px] gap-[5px] font-semibold ">
            <h3 className="text-[18px] font-bold">Henrique Schultz</h3>
            <div className="flex flex-row gap-[15px]">
                <p className="text-[13px]"><span className="text-[#C2CCD0]">Inicio: </span>22/09</p>
                <p className="text-[13px]"><span className="text-[#C2CCD0]">Fim: </span>26/09</p>
            </div>
            <p className="text-[13px]">20h/semana</p>
            <p className="text-[13px]">R$6,00/h</p>

        </div>
    );
};

export default Contrato;