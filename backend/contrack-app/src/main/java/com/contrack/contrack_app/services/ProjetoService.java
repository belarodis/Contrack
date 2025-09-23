package com.contrack.contrack_app.services;

import com.contrack.contrack_app.models.Alocacao;
import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Projeto;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import com.contrack.contrack_app.repositories.interfaces.IProjetoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    private final IProjetoRepository projetoRepository;
    private final IAlocacaoRepository alocacaoRepository;
    private final ContratoService contratoService;
    private final AlocacaoService alocacaoService;

    public ProjetoService(IProjetoRepository projetoRepository, IAlocacaoRepository alocacaoRepository, ContratoService contratoService, AlocacaoService alocacaoService) {
        this.projetoRepository = projetoRepository;
        this.alocacaoRepository = alocacaoRepository;
        this.contratoService = contratoService;
        this.alocacaoService = alocacaoService;
    }

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Optional<Projeto> buscarProjetoPorId(Long id) {
        return projetoRepository.findById(id);
    }

    public List<Projeto> buscarProjetos() {
        return projetoRepository.findAll();
    }

    // --- Lógica de cálculo de custo ---
    public double calcularCustoTotal(Projeto projeto) {
        double custoTotal = 0.0;

        // 1. Busca todas as alocações para este projeto.
        Iterable<Alocacao> alocacoes = alocacaoRepository.findByProjeto(projeto);

        // 2. Itera sobre cada alocação para calcular o custo de cada pessoa.
        for (Alocacao alocacao : alocacoes) {
            // 3. Busca o contrato ativo da pessoa alocada para obter o salário/hora.
            double salarioHora = contratoService.getContratoAtivo(alocacao.getPessoa())
                    .map(Contrato::getSalarioHora)
                    .orElse(0.0); // Retorna 0.0 se não houver contrato ativo.

            // 4. Calcula o custo da alocação e adiciona ao custo total do projeto.
            custoTotal += salarioHora * alocacao.getHorasSemana();
        }

        return custoTotal;
    }

    public boolean isProjetoAtivo(Projeto projeto) {
        LocalDate hoje = LocalDate.now();
        return !hoje.isAfter(projeto.getDataFim());
    }

    public boolean isProjetoOperacional(Projeto projeto) {

        boolean estaNoPrazo = isProjetoAtivo(projeto);
        boolean temEquipeMinima = alocacaoService.verificarComposicaoTime(projeto);

        return estaNoPrazo && temEquipeMinima;
    }
}