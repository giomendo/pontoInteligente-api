package br.com.estudos.springboot.pontointeligente.api.controllers;

import br.com.estudos.springboot.pontointeligente.api.dtos.EmpresaDto;
import br.com.estudos.springboot.pontointeligente.api.entities.Empresa;
import br.com.estudos.springboot.pontointeligente.api.response.Response;
import br.com.estudos.springboot.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private EmpresaService empresaService;

    public EmpresaController(){}


    /**
     * Retorna uma Empresa dado um CNPJ
     *
     * @param cnpj
     * @return ResponseEntity<Response<EmpresaDto>>
     */
    @GetMapping(value="/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
        log.info("Buscando empresa por CNPJ {}", cnpj);

        Response<EmpresaDto> response = new Response<EmpresaDto>();
        Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);

        if(!empresa.isPresent()) {
            log.error("Não encontrou nenhuma empresa com este CNPJ {}", cnpj);
            response.getErrors().add("Não encontrou nenhuma empresa com CNPJ " + cnpj);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterEmpresaDto(empresa.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Converte Empresa para EmpresaDTO
     *
     * @param empresa
     * @return EmpresaDto
     */
    private EmpresaDto converterEmpresaDto(Empresa empresa) {
        EmpresaDto empresaDto = new EmpresaDto();
        empresaDto.setRazaoSocial(empresa.getRazaoSocial());
        empresaDto.setCnpj(empresa.getCnpj());
        empresaDto.setId(empresa.getId());
        return empresaDto;
    }
}
