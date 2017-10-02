package br.com.estudos.springboot.pontointeligente.api.services.impl;

import br.com.estudos.springboot.pontointeligente.api.entities.Lancamento;
import br.com.estudos.springboot.pontointeligente.api.repository.FuncionarioRepository;
import br.com.estudos.springboot.pontointeligente.api.repository.LancamentoRepository;
import br.com.estudos.springboot.pontointeligente.api.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;


    @Override
    public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        log.info("Buscando Lancamentos por funcionario {}", funcionarioId);
        return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    public Optional<Lancamento> buscarPorId(Long id) {
        log.info("Buscando Lancamentos por ID {}", id);
        return Optional.ofNullable(this.lancamentoRepository.findOne(id));
    }

    @Override
    public Lancamento persistir(Lancamento lancamento) {
        log.info("Persistindo Lancamento {}", lancamento);
        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public void remover(Long id) {
        log.info("Removendo Lancamento de ID{}", id);
        this.lancamentoRepository.delete(id);
    }
}
