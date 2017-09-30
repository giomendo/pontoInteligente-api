package br.com.estudos.springboot.pontointeligente.api.services;

import br.com.estudos.springboot.pontointeligente.api.entities.Lancamento;
import br.com.estudos.springboot.pontointeligente.api.repository.LancamentoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;


    @Before
    public void setUp() {
        BDDMockito.given(lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
                .willReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
        BDDMockito.given(lancamentoRepository.findOne(Mockito.anyLong())).willReturn(new Lancamento());
        BDDMockito.given(lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
    }

    @Test
    public void testarBuscarLancamentoPorFuncionario() {
        Page<Lancamento> lancamentos = this.lancamentoService.buscarPorFuncionarioId(1l, new PageRequest(0, 10));

        assertNotNull(lancamentos);
    }

    @Test
    public void testarBuscarFuncionarioPorId() {
        Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(1l);

        assertTrue(lancamento.isPresent());
    }

    @Test
    public void persistirLancamento() {
        Lancamento lancamento = lancamentoService.persistir(new Lancamento());

        assertNotNull(lancamento);
    }
}
