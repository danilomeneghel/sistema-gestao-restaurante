package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import restaurante.model.Cliente;
import restaurante.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@Tag(name = "Cliente")
@Validated
public class ApiClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> mostrarClientes() {
        return new ResponseEntity<>(clienteService.findAllClientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(clienteService.findClienteById(id), HttpStatus.OK);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable String nome) {
        return new ResponseEntity<>(clienteService.findClienteByNome(nome), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteService.salvarCliente(cliente), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente usu = clienteService.findClienteById(id);
        if (usu == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        cliente.setId(usu.getId());
        return new ResponseEntity<>(clienteService.salvarCliente(cliente), HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirCliente(@PathVariable Long id) {
        clienteService.excluirClienteById(id);
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<Cliente>> pesquisarCliente(String pesquisa) {
        return new ResponseEntity<>(clienteService.findClienteByNomeIgnoreCase(pesquisa), HttpStatus.OK);
    }

}
