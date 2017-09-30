package br.com.estudos.springboot.pontointeligente.api.Controllers;


import br.com.estudos.springboot.pontointeligente.api.dtos.CadastroPJDto;
import br.com.estudos.springboot.pontointeligente.api.entities.Empresa;
import br.com.estudos.springboot.pontointeligente.api.entities.Funcionario;
import br.com.estudos.springboot.pontointeligente.api.enums.PerfilEnum;
import br.com.estudos.springboot.pontointeligente.api.response.Response;
import br.com.estudos.springboot.pontointeligente.api.services.EmpresaService;
import br.com.estudos.springboot.pontointeligente.api.services.FuncionarioService;
import br.com.estudos.springboot.pontointeligente.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;


    public CadastroPJController() {

    }

    /**
     * Cadastra um PJ
     *
     * @param cadastroPjDto
     * @param result
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPjDto,
                                                             BindingResult result) throws NoSuchAlgorithmException {

        log.info("Cadastrando PJ {}", cadastroPjDto.toString());
        Response<CadastroPJDto> response = new Response<CadastroPJDto>();

        validarDadosExistentes(cadastroPjDto, result);
        Empresa empresa = getConverterDTOParaEmpresa(cadastroPjDto);
        Funcionario funcionario = getConverterDTOParaFuncionario(cadastroPjDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando dados PJ {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.empresaService.persistir(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroPJDto(funcionario));
        return ResponseEntity.ok(response);
    }

    /**
     * converte DTO para Funcionario
     *
     * @param cadastroPjDto
     * @param result
     * @return
     */
    private Funcionario getConverterDTOParaFuncionario(CadastroPJDto cadastroPjDto, BindingResult result) throws NoSuchAlgorithmException {
        Funcionario func = new Funcionario();
        func.setCpf(cadastroPjDto.getCpf());
        func.setEmail(cadastroPjDto.getEmail());
        func.setNome(cadastroPjDto.getNome());
        func.setPerfil(PerfilEnum.ROLE_ADMIN);
        func.setSenha(PasswordUtils.gerarBCrypt(cadastroPjDto.getSenha()));
        return func;
    }

    /**
     * Converte DTO para Empresa
     *
     * @param cadastroPjDto
     * @return
     */
    private Empresa getConverterDTOParaEmpresa(CadastroPJDto cadastroPjDto) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPjDto.getCnpj());
        empresa.setRazaoSocial(cadastroPjDto.getRazaoSocial());
        return empresa;
    }

    /**
     * Verifica se empresa ou funcionario ja existem na base de dados
     *
     * @param pjDto
     * @param result
     */
    private void validarDadosExistentes(CadastroPJDto pjDto, BindingResult result) {
        this.empresaService.buscarPorCnpj(pjDto.getCnpj()).ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente.")));
        this.funcionarioService.buscarPorCpf(pjDto.getCpf()).ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));
        this.funcionarioService.buscarPorEmail(pjDto.getEmail()).ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
    }

    /**
     * Converte funcionario e emrpesa para DTO de cadastro
     *
     * @param funcionario
     * @return
     */
    private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
        CadastroPJDto dto = new CadastroPJDto();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setCpf(funcionario.getCpf());
        dto.setCnpj(funcionario.getEmpresa().getCnpj());
        dto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        return dto;
    }
}
