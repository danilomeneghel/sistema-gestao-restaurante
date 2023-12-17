package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Estabelecimento;
import restaurante.service.EstabelecimentoService;

import java.util.List;

@RestController
@RequestMapping("/api/estabelecimento")
@Tag(name = "Estabelecimento")
@Validated
public class ApiEstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping("/estabelecimentos")
    public ResponseEntity<List<Estabelecimento>> mostrarEstabelecimentos() {
        return new ResponseEntity<>(estabelecimentoService.findAllEstabelecimentos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estabelecimento> buscarEstabelecimentoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(estabelecimentoService.findEstabelecimentoById(id), HttpStatus.OK);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Estabelecimento> buscarEstabelecimento(@PathVariable String nome) {
        return new ResponseEntity<>(estabelecimentoService.findEstabelecimentoByNome(nome), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Estabelecimento> cadastrarEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return new ResponseEntity<>(estabelecimentoService.salvarEstabelecimento(estabelecimento), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Estabelecimento> editarEstabelecimento(@PathVariable Long id, @RequestBody Estabelecimento estabelecimento) {
        Estabelecimento usu = estabelecimentoService.findEstabelecimentoById(id);
        if (usu == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        estabelecimento.setId(usu.getId());
        return new ResponseEntity<>(estabelecimentoService.salvarEstabelecimento(estabelecimento), HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirEstabelecimento(@PathVariable Long id) {
        estabelecimentoService.excluirEstabelecimentoById(id);
    }

}
