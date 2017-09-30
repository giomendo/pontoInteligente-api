package br.com.estudos.springboot.pontointeligente.api.services.impl;

import br.com.estudos.springboot.pontointeligente.api.entities.Funcionario;
import br.com.estudos.springboot.pontointeligente.api.repository.FuncionarioRepository;
import br.com.estudos.springboot.pontointeligente.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Override
    public Funcionario persistir(Funcionario funcionario) {
        log.info("Persistindo Funcionario {}", funcionario);
        return this.funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        log.info("Buscando Funcionario por CPF {}", cpf);
        return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
        log.info("Buscando Funcionario por email {}", email);
        return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        log.info("Buscando Funcionario por ID {}", id);
        return Optional.ofNullable(this.funcionarioRepository.findOne(id));
    }
}
