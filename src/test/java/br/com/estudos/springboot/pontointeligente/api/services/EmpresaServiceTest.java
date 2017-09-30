package br.com.estudos.springboot.pontointeligente.api.services;

import br.com.estudos.springboot.pontointeligente.api.entities.Empresa;
import br.com.estudos.springboot.pontointeligente.api.repository.EmpresaRepository;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
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
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    private static final String CNPJ = "04069386000149";

    @Before
    public void setUp() {
        BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
        BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
    }

    @Test
    public void testarBuscarEmpresaPorCnpj() {
        Optional<Empresa> empresa = empresaService.buscarPorCnpj(CNPJ);
        assertTrue(empresa.isPresent());
    }

    @Test
    public void testarPersistirEmpresa() {
        Empresa empresa = empresaService.persistir(new Empresa());
        assertNotNull(empresa);
    }
}
