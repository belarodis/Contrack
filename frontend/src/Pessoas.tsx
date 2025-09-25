import Pessoa from "./Pessoa";
import ButtonPlus from "./ButtonPlus";
import ButtonSave from "./ButtonSave.tsx";
import ButtonExit from "./ButtonExit.tsx";
import {usePessoas} from "./apihooks/usePessoas.ts";
import type {PessoaDTO} from "./api/pessoasApi.ts";


export default function Pessoas() {
    const { data: pessoas, loading, error,} = usePessoas();

    return (
        <div className="bg-[#0A2439] flex flex-col flex-1 rounded-[25px] px-[48px] pt-[30px]">
            <div className="flex flex-row justify-between items-center">
                <h1 className="text-[#9DFFD9] text-[36px] font-semibold">Pessoas</h1>
                <ButtonPlus/>
                <ButtonSave/>
                <ButtonExit/>
            </div>

            {loading && <div className="pt-[15px] text-[#9DFFD9]/70">Carregando…</div>}
            {error && <div className="pt-[15px] text-red-400">{error}</div>}

            {!loading && !error && (
                <div className="grid grid-cols-3 gap-[1vw] pt-[15px]">
                    {(pessoas ?? []).map((p : PessoaDTO ) => (
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