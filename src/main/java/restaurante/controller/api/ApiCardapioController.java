package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Cardapio;
import restaurante.model.CardapioItem;
import restaurante.service.CardapioItemService;
import restaurante.service.CardapioService;

import java.util.List;

@RestController
@RequestMapping("/api/cardapio")
@Tag(name = "Cardapio")
@Validated
public class ApiCardapioController {

    @Autowired
    private CardapioService cardapioService;

    @Autowired
    private CardapioItemService cardapioItemService;

    @GetMapping("/cardapios")
    public ResponseEntity<List<Cardapio>> mostrarCardapios() {
        return new ResponseEntity<>(cardapioService.findAllCardapios(), HttpStatus.OK);
    }

    @PostMapping("/cardapio/cadastro")
    public ResponseEntity<Cardapio> cadastroCardapio(@RequestBody Cardapio cardapio) {
        return new ResponseEntity<>(cardapioService.salvarCardapio(cardapio), HttpStatus.CREATED);
    }

    @PutMapping("/cardapio/editar/{id}")
    public ResponseEntity<Cardapio> editarCardapio(@PathVariable Long id, @RequestBody Cardapio cardapio) {
        Cardapio cat = cardapioService.findCardapioById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        cardapio.setId(cat.getId());
        return new ResponseEntity<>(cardapioService.salvarCardapio(cardapio), HttpStatus.OK);
    }

    @DeleteMapping("/cardapio/excluir/{id}")
    public void excluirCardapio(@PathVariable Long id) {
        cardapioService.excluirCardapio(id);
    }

    @GetMapping("/cardapio/pesquisa")
    public ResponseEntity<List<Cardapio>> pesquisarCardapio(String pesquisa) {
        return new ResponseEntity<>(cardapioService.findCardapioByNome(pesquisa), HttpStatus.OK);
    }

    @GetMapping("/cardapio-itens")
    public ResponseEntity<List<CardapioItem>> mostrarCardapioItens() {
        return new ResponseEntity<>(cardapioItemService.findAllCardapioItens(), HttpStatus.OK);
    }

    @PostMapping("/cardapio-item/cadastro")
    public ResponseEntity<CardapioItem> cadastroCardapioItem(@RequestBody CardapioItem cardapioItem) {
        return new ResponseEntity<>(cardapioItemService.salvarCardapioItem(cardapioItem), HttpStatus.CREATED);
    }

    @PutMapping("/cardapio-item/editar/{id}")
    public ResponseEntity<CardapioItem> editarCardapioItem(@PathVariable Long id, @RequestBody CardapioItem cardapioItem) {
        CardapioItem cat = cardapioItemService.findCardapioItemById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        cardapioItem.setId(cat.getId());
        return new ResponseEntity<>(cardapioItemService.salvarCardapioItem(cardapioItem), HttpStatus.OK);
    }

    @DeleteMapping("/cardapio-item/excluir/{id}")
    public void excluirCardapioItem(@PathVariable Long id) {
        cardapioItemService.excluirCardapioItem(id);
    }

    @GetMapping("/cardapio-item/pesquisa")
    public ResponseEntity<List<CardapioItem>> pesquisarCardapioItem(String pesquisa) {
        return new ResponseEntity<>(cardapioItemService.findCardapioItemByNome(pesquisa), HttpStatus.OK);
    }

}
