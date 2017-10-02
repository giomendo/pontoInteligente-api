package br.com.estudos.springboot.pontointeligente.api.services;

import br.com.estudos.springboot.pontointeligente.api.entities.Funcionario;
import br.com.estudos.springboot.pontointeligente.api.repository.FuncionarioRepository;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;


    @Before
    public void setUp() {
        given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
        given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
        given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
        given(this.funcionarioRepository.findOne(Mockito.anyLong())).willReturn(new Funcionario());
    }

    @Test
    public void testarPersistirFuncionario() {
        Funcionario funcionario = funcionarioService.persistir(new Funcionario());

        assertNotNull(funcionario);
    }

    @Test
    public void testarBuscarFuncionarioPorId() {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorId(1l);

        assertTrue(funcionario.isPresent());
    }

    @Test
    public void testarBuscarFuncionarioPorEmail() {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail("bla@bla.com.br");

        assertTrue(funcionario.isPresent());
    }

    @Test
    public void testarBuscarFuncionarioPorCpf() {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorCpf("68334044020");

        assertTrue(funcionario.isPresent());
    }

}
