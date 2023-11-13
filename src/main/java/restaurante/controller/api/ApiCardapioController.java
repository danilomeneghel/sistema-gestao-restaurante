package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Cardapio;
import restaurante.service.CardapioService;

import java.util.List;

@RestController
@RequestMapping("/api/cardapio")
@Tag(name = "Cardapio")
@Validated
public class ApiCardapioController {

    @Autowired
    private CardapioService classficadorService;

    @GetMapping("/cardapios")
    public ResponseEntity<List<Cardapio>> mostrarCardapios() {
        return new ResponseEntity<>(classficadorService.findAllCardapios(), HttpStatus.OK);
    }

    @PostMapping("/cardapio/cadastro")
    public ResponseEntity<Cardapio> cadastroCardapio(@RequestBody Cardapio cardapio) {
        return new ResponseEntity<>(classficadorService.salvarCardapio(cardapio), HttpStatus.CREATED);
    }

    @PutMapping("/cardapio/editar/{id}")
    public ResponseEntity<Cardapio> editarCardapio(@PathVariable Long id, @RequestBody Cardapio cardapio) {
        Cardapio cat = classficadorService.findCardapioById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        cardapio.setId(cat.getId());
        return new ResponseEntity<>(classficadorService.salvarCardapio(cardapio), HttpStatus.OK);
    }

    @DeleteMapping("/cardapio/excluir/{id}")
    public void excluirCardapio(@PathVariable Long id) {
        classficadorService.excluirCardapio(id);
    }

    @GetMapping("/cardapio/pesquisa")
    public ResponseEntity<List<Cardapio>> pesquisarCardapio(String pesquisa) {
        return new ResponseEntity<>(classficadorService.findCardapioByNome(pesquisa), HttpStatus.OK);
    }

}
