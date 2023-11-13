package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Bairro;
import restaurante.model.Estado;
import restaurante.model.Municipio;
import restaurante.service.LocalidadeService;

import java.util.List;

@RestController
@RequestMapping("/api/localidade")
@Tag(name = "Localidade")
@Validated
public class ApiLocalidadeController {

    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping("/bairros")
    public ResponseEntity<List<Bairro>> mostrarBairros() {
        return new ResponseEntity<>(localidadeService.findAllBairros(), HttpStatus.OK);
    }

    @PostMapping("/bairro/cadastro")
    public ResponseEntity<Bairro> cadastroBairro(@RequestBody Bairro bairro) {
        return new ResponseEntity<>(localidadeService.salvarBairro(bairro), HttpStatus.CREATED);
    }

    @PutMapping("/bairro/editar/{id}")
    public ResponseEntity<Bairro> editarBairro(@PathVariable Long id, @RequestBody Bairro bairro) {
        Bairro bai = localidadeService.findBairroById(id);
        if (bai == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        bairro.setId(bai.getId());
        return new ResponseEntity<>(localidadeService.salvarBairro(bairro), HttpStatus.OK);
    }

    @DeleteMapping("/bairro/excluir/{id}")
    public void excluirBairro(@PathVariable Long id) {
        localidadeService.excluirBairroById(id);
    }

    @GetMapping("/bairro/pesquisa")
    public ResponseEntity<List<Bairro>> pesquisarBairro(String pesquisa) {
        return new ResponseEntity<>(localidadeService.findBairroByNome(pesquisa), HttpStatus.OK);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<Estado>> mostrarEstados() {
        return new ResponseEntity<>(localidadeService.findAllEstados(), HttpStatus.OK);
    }

    @PostMapping("/estado/cadastro")
    public ResponseEntity<Estado> cadastroEstado(@RequestBody Estado estado) {
        return new ResponseEntity<>(localidadeService.salvarEstado(estado), HttpStatus.CREATED);
    }

    @PutMapping("/estado/editar/{id}")
    public ResponseEntity<Estado> editarEstado(@PathVariable Long id, @RequestBody Estado estado) {
        Estado est = localidadeService.findEstadoById(id);
        if (est == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        estado.setId(est.getId());
        return new ResponseEntity<>(localidadeService.salvarEstado(estado), HttpStatus.OK);
    }

    @DeleteMapping("/estado/excluir/{id}")
    public void excluirEstado(@PathVariable Long id) {
        localidadeService.excluirEstadoById(id);
    }

    @GetMapping("/estado/pesquisa")
    public ResponseEntity<List<Estado>> pesquisarEstado(String pesquisa) {
        return new ResponseEntity<>(localidadeService.findEstadoByNome(pesquisa), HttpStatus.OK);
    }

    @GetMapping("/municipios")
    public ResponseEntity<List<Municipio>> mostrarMunicipios() {
        return new ResponseEntity<>(localidadeService.findAllMunicipios(), HttpStatus.OK);
    }

    @PostMapping("/municipio/cadastro")
    public ResponseEntity<Municipio> cadastroMunicipio(@RequestBody Municipio municipio) {
        return new ResponseEntity<>(localidadeService.salvarMunicipio(municipio), HttpStatus.CREATED);
    }

    @PutMapping("/municipio/editar/{id}")
    public ResponseEntity<Municipio> editarMunicipio(@PathVariable Long id, @RequestBody Municipio municipio) {
        Municipio mun = localidadeService.findMunicipioById(id);
        if (mun == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        municipio.setId(mun.getId());
        return new ResponseEntity<>(localidadeService.salvarMunicipio(municipio), HttpStatus.OK);
    }

    @DeleteMapping("/municipio/excluir/{id}")
    public void excluirMunicipio(@PathVariable Long id) {
        localidadeService.excluirMunicipioById(id);
    }

    @GetMapping("/municipio/pesquisa")
    public ResponseEntity<List<Municipio>> pesquisarMunicipio(String pesquisa) {
        return new ResponseEntity<>(localidadeService.findMunicipioByNome(pesquisa), HttpStatus.OK);
    }

}
