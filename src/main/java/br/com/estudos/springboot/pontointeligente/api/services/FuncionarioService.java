package br.com.estudos.springboot.pontointeligente.api.services;

import br.com.estudos.springboot.pontointeligente.api.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

    /**
     * Persiste um Funcionario no banco de dados
     *
     * @param funcionario
     * @return
     */
    Funcionario persistir(Funcionario funcionario);

    /**
     * Busca Funcionarios por CPF
     *
     * @param cpf
     * @return
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Busca Funcionarios por email
     *
     * @param email
     * @return
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Busca um fucnionario por Id
     *
     * @param id
     * @return
     */
    Optional<Funcionario> buscarPorId(Long id);

}
