package restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import restaurante.enums.Ativo;
import restaurante.enums.Role;
import restaurante.model.Usuario;
import restaurante.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/todos")
    public ModelAndView mostrarUsuarios() {
        ModelAndView mv = new ModelAndView("usuario/usuarios");
        mv.addObject("usuarios", usuarioService.findAllUsuarios());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroUsuario() {
        ModelAndView mv = new ModelAndView("usuario/usuarioCadastro");
        mv.addObject("usuario", new Usuario());
        mv.addObject("active", Ativo.values());
        mv.addObject("roles", Role.values());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarUsuario(@Validated Usuario usuario, Errors errors) {
        ModelAndView mv = new ModelAndView("usuario/usuarioCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Usuário foi cadastrado com sucesso!");
        usuarioService.salvarUsuario(usuario);
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaUsuario(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("usuario/usuarioEditar");
        Usuario usuario = usuarioService.findUsuarioById(id);
        mv.addObject("usuario", usuario);
        mv.addObject("valorAtivo", usuario.isActive());
        mv.addObject("active", Ativo.values());
        mv.addObject("valorRole", usuario.getRoles());
        mv.addObject("roles", Role.values());
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarUsuario(@Validated Usuario usuario, Errors errors) {
        ModelAndView mv = new ModelAndView("usuario/usuarioEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Usuário foi atualizado com sucesso!");
        usuarioService.salvarUsuario(usuario);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirUsuario(@PathVariable Long id, RedirectAttributes ra) {
        Usuario usuario = usuarioService.findUsuarioById(id);
        if(usuario != null) {
            usuarioService.excluirUsuarioById(id);
            ra.addFlashAttribute("sucesso", "O Usuário foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Usuário não foi encontrado.");
        }
        return new ModelAndView("redirect:/usuario/todos");
    }

}
