package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Categoria;
import restaurante.model.Produto;
import restaurante.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/classificador")
@Tag(name = "Classificador")
@Validated
public class ApiClassificadorController {

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

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> mostrarProdutos() {
        return new ResponseEntity<>(classficadorService.findAllProdutos(), HttpStatus.OK);
    }

    @PostMapping("/produto/cadastro")
    public ResponseEntity<Produto> cadastroProduto(@RequestBody Produto produto) {
        return new ResponseEntity<>(classficadorService.salvarProduto(produto), HttpStatus.CREATED);
    }

    @PutMapping("/produto/editar/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto neg = classficadorService.findProdutoById(id);
        if (neg == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        produto.setId(neg.getId());
        return new ResponseEntity<>(classficadorService.salvarProduto(produto), HttpStatus.OK);
    }

    @DeleteMapping("/produto/excluir/{id}")
    public void excluirProduto(@PathVariable Long id) {
        classficadorService.excluirProduto(id);
    }

    @GetMapping("/produto/pesquisa")
    public ResponseEntity<List<Produto>> pesquisarProduto(String pesquisa) {
        return new ResponseEntity<>(classficadorService.findProdutoByNome(pesquisa), HttpStatus.OK);
    }

    @GetMapping("/quartos")
    public ResponseEntity<List<Quarto>> mostrarQuartos() {
        return new ResponseEntity<>(classficadorService.findAllQuartos(), HttpStatus.OK);
    }

    @PostMapping("/quarto/cadastro")
    public ResponseEntity<Quarto> cadastroQuarto(@RequestBody Quarto quarto) {
        return new ResponseEntity<>(classficadorService.salvarQuarto(quarto), HttpStatus.CREATED);
    }

    @PutMapping("/quarto/editar/{id}")
    public ResponseEntity<Quarto> editarQuarto(@PathVariable Long id, @RequestBody Quarto quarto) {
        Quarto qua = classficadorService.findQuartoById(id);
        if (qua == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        quarto.setId(qua.getId());
        return new ResponseEntity<>(classficadorService.salvarQuarto(quarto), HttpStatus.OK);
    }

    @DeleteMapping("/quarto/excluir/{id}")
    public void excluirQuarto(@PathVariable Long id) {
        classficadorService.excluirQuarto(id);
    }

    @GetMapping("/quarto/pesquisa")
    public ResponseEntity<List<Quarto>> pesquisarQuarto(Integer pesquisa) {
        return new ResponseEntity<>(classficadorService.findQuartoByQuantidade(pesquisa), HttpStatus.OK);
    }

}
