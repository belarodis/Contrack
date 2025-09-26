package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.ProjetoCreateDTO;
import com.contrack.contrack_app.dto.view.ProjetoViewDTO;
import com.contrack.contrack_app.exceptions.ResourceNotFoundException;
import com.contrack.contrack_app.mapper.ProjetoMapper;
import com.contrack.contrack_app.models.Alocacao;
import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Projeto;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import com.contrack.contrack_app.repositories.interfaces.IProjetoRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjetoService {

    private final IProjetoRepository projetoRepository;
    private final IAlocacaoRepository alocacaoRepository;
    private final ContratoService contratoService;
    private final ProjetoMapper projetoMapper;

    public ProjetoService(IProjetoRepository projetoRepository, IAlocacaoRepository alocacaoRepository, ContratoService contratoService, ProjetoMapper projetoMapper) {
        this.projetoRepository = projetoRepository;
        this.alocacaoRepository = alocacaoRepository;
        this.contratoService = contratoService;
        this.projetoMapper = projetoMapper;
    }

    public ProjetoViewDTO criarProjeto(ProjetoCreateDTO dto) {
        Projeto projeto = projetoMapper.toEntity(dto);
        Projeto projetoSalvo = projetoRepository.save(projeto);
        return projetoMapper.toDto(projetoSalvo);
    }

    public Optional<Projeto> buscarProjetoPorId(Long id) {
        return projetoRepository.findById(id);
    }

    public List<ProjetoViewDTO> buscarProjetos() {
        return projetoRepository.findAll()
                .stream()
                .map(projetoMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Calcula o custo TOTAL PROJETADO (do início ao fim do projeto).
     * Considera apenas dias úteis na interseção de Projeto x Contrato.
     */
    public double calcularCustoTotal(Projeto projeto) {
        double custoTotal = 0.0;
        Iterable<Alocacao> alocacoes = alocacaoRepository.findByProjeto(projeto);

        // O status é checado apenas para a regra de negócio de não calcular
        // projetos 'Concluídos' se não for o custo final.
        String status = buscarProjetoPorIdComStatus(projeto.getId())
            .map(dto -> dto.status())
            .orElse("Concluído");

        if (!("Ativo".equals(status) || "Incompleto".equals(status))) {
            return 0.0;
        }

        // PERÍODO DE CÁLCULO: INÍCIO A FIM DO PROJETO (Custo Projetado)
        LocalDate inicioCalculo = projeto.getDataInicio();
        LocalDate fimCalculo = projeto.getDataFim(); 

        if (inicioCalculo.isAfter(fimCalculo)) {
            return 0.0;
        }

        final double DIAS_UTEIS_SEMANA = 5.0;

        for (Alocacao alocacao : alocacoes) {
            if (alocacao.getHorasSemana() <= 0) continue; 
            
            // Custo Diário por Hora Alocada: (Horas Alocadas na Semana / 5 dias úteis)
            final double horasDiariasAlocadas = alocacao.getHorasSemana() / DIAS_UTEIS_SEMANA; 

            // Busca TODOS os contratos que se interseccionam com o período completo do projeto
            List<Contrato> contratos = contratoService.getContratosNosPeriodo(
                alocacao.getPessoa(), inicioCalculo, fimCalculo);

            for (Contrato contrato : contratos) {
                
                // 2. Calcular a INTERSEÇÃO real entre (Contrato) e (Período do Projeto)
                LocalDate intersecaoInicio = Stream.of(inicioCalculo, contrato.getDataInicio())
                    .max(LocalDate::compareTo).get();
                LocalDate intersecaoFim = Stream.of(fimCalculo, contrato.getDataFim())
                    .min(LocalDate::compareTo).get();

                if (intersecaoInicio.isAfter(intersecaoFim)) {
                    continue; // Não há sobreposição válida
                }
                
                // 3. Itera sobre o período de interseção, dia a dia.
                custoTotal += Stream.iterate(intersecaoInicio, data -> data.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(intersecaoInicio, intersecaoFim) + 1)
                    // FILTRO DE DIAS ÚTEIS: Garante que apenas Seg-Sex sejam contados
                    .filter(data -> data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY)
                    .mapToDouble(data -> {
                        // Custo Diário = Salário por Hora * (Horas Alocadas Diariamente)
                        return contrato.getSalarioHora() * horasDiariasAlocadas;
                    })
                    .sum();
            }
        }
        return custoTotal;
    }

    /**
     * Calcula o custo do projeto em um período definido (histórico ou parcial).
     * Considera apenas dias úteis na interseção de Projeto x Contrato x Período Solicitado.
     */
    public double calcularCustoPorPeriodo(Projeto projeto, LocalDate periodoInicio, LocalDate periodoFim) {
        double custoTotal = 0.0;
        Iterable<Alocacao> alocacoes = alocacaoRepository.findByProjeto(projeto);

        // Calcula a INTERSEÇÃO entre o período solicitado e o período do Projeto
        LocalDate inicioReal = Stream.of(periodoInicio, projeto.getDataInicio()).max(LocalDate::compareTo).get();
        LocalDate fimReal = Stream.of(periodoFim, projeto.getDataFim()).min(LocalDate::compareTo).get();

        if (inicioReal.isAfter(fimReal)) {
            return 0.0;
        }
        
        final double DIAS_UTEIS_SEMANA = 5.0;

        for (Alocacao alocacao : alocacoes) {
            if (alocacao.getHorasSemana() <= 0) continue;
            final double horasDiariasAlocadas = alocacao.getHorasSemana() / DIAS_UTEIS_SEMANA;

            // Busca TODOS os contratos que se interseccionam com o período real de cálculo
            List<Contrato> contratos = contratoService.getContratosNosPeriodo(
                alocacao.getPessoa(), inicioReal, fimReal);

            for (Contrato contrato : contratos) {
                
                // Interseção Tripla: (Período Solicitado ∩ Projeto) ∩ (Contrato)
                LocalDate intersecaoInicio = Stream.of(inicioReal, contrato.getDataInicio()).max(LocalDate::compareTo).get();
                LocalDate intersecaoFim = Stream.of(fimReal, contrato.getDataFim()).min(LocalDate::compareTo).get();

                if (!intersecaoInicio.isAfter(intersecaoFim)) {
                    // Itera e soma apenas os dias úteis na interseção
                    custoTotal += Stream.iterate(intersecaoInicio, data -> data.plusDays(1))
                        .limit(ChronoUnit.DAYS.between(intersecaoInicio, intersecaoFim) + 1)
                        .filter(data -> data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY)
                        .mapToDouble(data -> contrato.getSalarioHora() * horasDiariasAlocadas)
                        .sum();
                }
            }
        }

        return custoTotal;
    }

    public boolean isProjetoAtivo(Projeto projeto) {
        LocalDate hoje = LocalDate.now();
        return !hoje.isAfter(projeto.getDataFim());
    }

    public Optional<ProjetoViewDTO> buscarProjetoPorIdComStatus(Long id) {
        return projetoRepository.findById(id)
                .map(projetoMapper::toDto);
    }
    
    public Projeto buscarProjetoPorIdOrThrow(Long id) {
        return projetoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projeto", id));
    }
}