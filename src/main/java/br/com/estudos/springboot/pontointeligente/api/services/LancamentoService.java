package br.com.estudos.springboot.pontointeligente.api.services;

import br.com.estudos.springboot.pontointeligente.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

public interface LancamentoService {


    /**
     * Retorna a lista paginada de Lancamentos por ID de funcionario
     *
     * @param funcionarioId
     * @param pageRequest
     * @return
     */
    Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Buscar Lancamento por ID
     *
     * @param id
     * @return
     */
    Optional<Lancamento> buscarPorId(Long id);

    /**
     * Persistir um Lancamento na Base de Dados
     *
     * @return
     */
    Lancamento persistir(Lancamento lancamento);

    /**
     * Excluir um lancamento pelo ID
     *
     * @param id
     */
    void remover(Long id);

}
