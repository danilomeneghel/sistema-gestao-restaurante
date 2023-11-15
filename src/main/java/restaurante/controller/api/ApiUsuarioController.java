package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restaurante.model.Usuario;
import restaurante.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuário")
@Validated
public class ApiUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> mostrarUsuarios() {
        return new ResponseEntity<>(usuarioService.findAllUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.findUsuarioById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String username) {
        return new ResponseEntity<>(usuarioService.findUsuarioByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.salvarUsuario(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/usuario/editar/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usu = usuarioService.findUsuarioById(id);
        if (usu == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        usuario.setId(usu.getId());
        return new ResponseEntity<>(usuarioService.salvarUsuario(usuario), HttpStatus.OK);
    }

    @DeleteMapping("/usuario/excluir/{id}")
    public void excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuarioById(id);
    }

}
