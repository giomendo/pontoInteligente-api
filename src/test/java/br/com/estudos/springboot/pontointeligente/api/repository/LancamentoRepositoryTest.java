package br.com.estudos.springboot.pontointeligente.api.repository;

import br.com.estudos.springboot.pontointeligente.api.entities.Empresa;
import br.com.estudos.springboot.pontointeligente.api.entities.Funcionario;
import br.com.estudos.springboot.pontointeligente.api.entities.Lancamento;
import br.com.estudos.springboot.pontointeligente.api.enums.PerfilEnum;
import br.com.estudos.springboot.pontointeligente.api.enums.TipoEnum;
import br.com.estudos.springboot.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private LancamentoRepository lancamentoRepository;

    private static final String CPF = "68334044020";
    private static final String EMAIL = "giomendo@gmail.com";

    private Long funcionarioId;

    @Before
    public void setUp() {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();
        this.lancamentoRepository.save(obterDadosLancamento(funcionario));
        this.lancamentoRepository.save(obterDadosLancamento(funcionario));
    }

    @After
    public void tearDown() {
        this.empresaRepository.deleteAll();
    }

    @Test
    public void buscarLancamentoPorFuncionarioId() {
        List<Lancamento> listaLancamento = lancamentoRepository.findByFuncionarioId(funcionarioId);

        Assert.assertEquals(2, listaLancamento.size());
    }

    @Test
    public void buscarLancamentoPorFuncionarioIdPaginado() {

        PageRequest pageable = new PageRequest(0, 10);
        Page<Lancamento> listaLancamento = lancamentoRepository.findByFuncionarioId(funcionarioId, pageable);

        Assert.assertEquals(2, listaLancamento.getTotalElements());
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

    private Lancamento obterDadosLancamento(Funcionario funcionario) {
        Lancamento lancamento = new Lancamento();
        lancamento.setFuncionario(funcionario);
        lancamento.setData(new Date());
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        lancamento.setLocalizacao("Veranopolis");
        return lancamento;
    }
}
