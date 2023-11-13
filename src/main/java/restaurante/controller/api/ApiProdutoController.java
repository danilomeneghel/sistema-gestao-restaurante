package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Produto;
import restaurante.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Produto")
@Validated
public class ApiProdutoController {

    @Autowired
    private ProdutoService classficadorService;

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
        Produto cat = classficadorService.findProdutoById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        produto.setId(cat.getId());
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

}
