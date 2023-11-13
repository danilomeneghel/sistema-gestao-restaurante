package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Pedido;
import restaurante.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
@Tag(name = "Imóvel")
@Validated
public class ApiImovelController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/todos")
    public ResponseEntity<List<Pedido>> mostrarImoveis() {
        return new ResponseEntity<>(pedidoService.findAllImoveis(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarImovelPorId(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.findImovelById(id), HttpStatus.OK);
    }

    @PostMapping("/pedido/cadastro")
    public ResponseEntity<Pedido> cadastrarImovel(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.salvarImovel(pedido), HttpStatus.CREATED);
    }

    @PutMapping("/pedido/editar/{id}")
    public ResponseEntity<Pedido> editarImovel(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido imo = pedidoService.findImovelById(id);
        if (imo == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        pedido.setId(imo.getId());
        return new ResponseEntity<>(pedidoService.salvarImovel(pedido), HttpStatus.OK);
    }

    @DeleteMapping("/pedido/excluir/{id}")
    public void excluirImovel(@PathVariable Long id) {
        pedidoService.excluirImovelById(id);
    }

}
