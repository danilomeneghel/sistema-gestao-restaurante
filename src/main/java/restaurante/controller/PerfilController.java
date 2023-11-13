package restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import restaurante.service.UsuarioService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/{username}")
    public ModelAndView perfil(@PathVariable String username) {
        ModelAndView mv = new ModelAndView("usuario/perfil");
        mv.addObject("usuario", usuarioService.findUsuarioByUsername(username));
        return mv;
    }

}
