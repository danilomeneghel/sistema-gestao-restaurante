package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Categoria;
import restaurante.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@Tag(name = "Categoria")
@Validated
public class ApiCategoriaController {

    @Autowired
    private CategoriaService classficadorService;

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> mostrarCategorias() {
        return new ResponseEntity<>(classficadorService.findAllCategorias(), HttpStatus.OK);
    }

    @PostMapping("/categoria/cadastro")
    public ResponseEntity<Categoria> cadastroCategoria(@RequestBody Categoria categoria) {
        return new ResponseEntity<>(classficadorService.salvarCategoria(categoria), HttpStatus.CREATED);
    }

    @PutMapping("/categoria/editar/{id}")
    public ResponseEntity<Categoria> editarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria cat = classficadorService.findCategoriaById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        categoria.setId(cat.getId());
        return new ResponseEntity<>(classficadorService.salvarCategoria(categoria), HttpStatus.OK);
    }

    @DeleteMapping("/categoria/excluir/{id}")
    public void excluirCategoria(@PathVariable Long id) {
        classficadorService.excluirCategoria(id);
    }

    @GetMapping("/categoria/pesquisa")
    public ResponseEntity<List<Categoria>> pesquisarCategoria(String pesquisa) {
        return new ResponseEntity<>(classficadorService.findCategoriaByNome(pesquisa), HttpStatus.OK);
    }

}
