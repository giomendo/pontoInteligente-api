package br.com.estudos.springboot.pontointeligente.api.repository;

import br.com.estudos.springboot.pontointeligente.api.entities.Empresa;
import br.com.estudos.springboot.pontointeligente.api.entities.Funcionario;
import br.com.estudos.springboot.pontointeligente.api.enums.PerfilEnum;
import br.com.estudos.springboot.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private static final String CPF = "68334044020";
    private static final String EMAIL = "giomendo@gmail.com";

    @Before
    public void setUp() {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioRepository.save(obterDadosFuncionario(empresa));
    }

    @After
    public void tearDown() {
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Funcionario func = this.funcionarioRepository.findByEmail(EMAIL);

        assertEquals(EMAIL, func.getEmail());
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        Funcionario func = this.funcionarioRepository.findByCpf(CPF);

        assertEquals(CPF, func.getCpf());
    }

    @Test
    public void testBuscarFuncionarioPorEmailOuCpf() {
        Funcionario func = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);

        assertNotNull(func);
    }

    @Test
    public void testBuscarFuncionarioPorEmailOuCpfComEmailInvalido() {
        Funcionario func = this.funcionarioRepository.findByCpfOrEmail(CPF, "blabal@bla.bla");

        assertNotNull(func);
    }

    @Test
    public void testBuscarFuncionarioPorEmailOuCpfComCpfInvalido() {
        Funcionario func = this.funcionarioRepository.findByCpfOrEmail("123.123.123.12", EMAIL);

        assertNotNull(func);
    }


    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("25063739000173");
        empresa.setRazaoSocial("Empresa Teste");
        return empresa;
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) {
        Funcionario func = new Funcionario();
        func.setEmpresa(empresa);
        func.setNome("Giovano");
        func.setCpf(CPF);
        func.setEmail(EMAIL);
        func.setSenha(PasswordUtils.gerarBCrypt("12345"));
        func.setPerfil(PerfilEnum.ROLE_USUARIO);
        return func;
    }
}
