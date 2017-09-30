package br.com.estudos.springboot.pontointeligente.api.services;

import br.com.estudos.springboot.pontointeligente.api.entities.Empresa;

import java.util.Optional;

/**
 * Created by giovano.mendo on 30/09/2017.
 */
public interface EmpresaService {

    /**
     * Busca Empresa por CNPJ
     *
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> buscarPorCnpj(String cnpj);

    /**
     * Persiste um Objeto Empresa
     *
     * @param empresa
     * @return Empresa
     */
    Empresa persistir(Empresa empresa);
}
