package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Fornecedor;
import restaurante.service.FornecedorService;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
@Tag(name = "Fornecedor")
@Validated
public class ApiFornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/fornecedors")
    public ResponseEntity<List<Fornecedor>> mostrarFornecedores() {
        return new ResponseEntity<>(fornecedorService.findAllFornecedores(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarFornecedorPorId(@PathVariable Long id) {
        return new ResponseEntity<>(fornecedorService.findFornecedorById(id), HttpStatus.OK);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Fornecedor> buscarFornecedor(@PathVariable String nome) {
        return new ResponseEntity<>(fornecedorService.findFornecedorByNome(nome), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Fornecedor> cadastrarFornecedor(@RequestBody Fornecedor fornecedor) {
        return new ResponseEntity<>(fornecedorService.salvarFornecedor(fornecedor), HttpStatus.CREATED);
    }

    @PutMapping("/fornecedor/editar/{id}")
    public ResponseEntity<Fornecedor> editarFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {
        Fornecedor usu = fornecedorService.findFornecedorById(id);
        if (usu == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        fornecedor.setId(usu.getId());
        return new ResponseEntity<>(fornecedorService.salvarFornecedor(fornecedor), HttpStatus.OK);
    }

    @DeleteMapping("/fornecedor/excluir/{id}")
    public void excluirFornecedor(@PathVariable Long id) {
        fornecedorService.excluirFornecedorById(id);
    }

}
