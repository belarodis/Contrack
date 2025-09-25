import Pessoa from "./Pessoa";
import ButtonPlus from "./ButtonPlus";
import { usePessoas } from './hooks/usePessoa.ts';


export default function Pessoas() {
    const { data: pessoas, loading, error,} = usePessoas();

    return (
        <div className="bg-[#0A2439] flex flex-col flex-1 rounded-[25px] px-[48px] pt-[30px]">
            <div className="flex flex-row justify-between items-center">
                <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Pessoas</h1>
                <ButtonPlus/>
            </div>

            {loading && <div className="pt-[15px] text-[#9DFFD9]/70">Carregando…</div>}
            {error && <div className="pt-[15px] text-red-400">{error}</div>}

            {!loading && !error && (
                <div className="grid grid-cols-3 gap-[1vw] pt-[15px]">
                    {(pessoas ?? []).map((p) => (
                        <div key={p.id} className="w-full h-full flex">
                            <Pessoa nome={p.nome} />
                        </div>
                    ))}
                    {(!pessoas || pessoas.length === 0) && (
                        <div className="text-[#9DFFD9]/70 col-span-3">Nenhuma pessoa ainda.</div>
                    )}
                </div>
            )}
        </div>
    );
}