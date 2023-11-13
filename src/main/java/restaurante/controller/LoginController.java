package restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import restaurante.model.Usuario;
import restaurante.service.UsuarioService;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("security/login");
        return mv;
    }

    @GetMapping("/signup")
    public ModelAndView cadastroUsuario() {
        ModelAndView mv = new ModelAndView("security/signup");
        mv.addObject("user", new Usuario());
        return mv;
    }

    @PostMapping("/signup")
    public ModelAndView cadastrarUsuario(@Validated Usuario usuario, Errors errors) {
        ModelAndView mv = new ModelAndView("security/signup");
        if (usuarioService.emailExistente(usuario.getEmail())) {
            mv.addObject("customMessage", "O e-mail já foi cadastrado");
            mv.addObject("erroEmail", true);
            return mv;
        }
        if (errors.hasErrors() || usuario.getName().isEmpty() || usuario.getUsername().isEmpty() || usuario.getPassword().isEmpty()) {
            mv.addObject("customMessage", "Revise os campos obrigatórios");
            mv.setViewName("security/signup");
            return mv;
        }
        usuario.setActive(true);
        usuario.setRoles("ROLE_USER");
        usuarioService.salvarUsuario(usuario);
        return new ModelAndView("redirect:/login?success");
    }

}
