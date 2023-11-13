package restaurante.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return new ModelAndView("redirect:/pedido/pedidos-confirmados");
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            mv = new ModelAndView("indexAdmin");
        }
        mv.addObject("index", true);
        return mv;
    }

}
