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
import restaurante.model.Categoria;
import restaurante.service.CategoriaService;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ModelAndView mostrarCategorias() {
        ModelAndView mv = new ModelAndView("categoria/categorias");
        mv.addObject("categorias", categoriaService.findAllCategorias());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroCategoria() {
        ModelAndView mv = new ModelAndView("categoria/categoriaCadastro");
        mv.addObject("categoria", new Categoria());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarCategoria(@Validated Categoria categoria, Errors errors) {
        ModelAndView mv = new ModelAndView("categoria/categoriaCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "A Categoria foi cadastrada com sucesso!");
        categoriaService.salvarCategoria(categoria);
        mv.addObject("categoria", new Categoria());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaCategoria(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("categoria/categoriaEditar");
        mv.addObject("categoria", categoriaService.findCategoriaById(id));
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarCategoria(@Validated Categoria categoria, Errors errors) {
        ModelAndView mv = new ModelAndView("categoria/categoriaEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "A Categoria foi atualizada com sucesso!");
        categoriaService.salvarCategoria(categoria);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirCategoria(@PathVariable Long id, RedirectAttributes ra) {
        Categoria categoria = categoriaService.findCategoriaById(id);
        if (categoria != null) {
            categoriaService.excluirCategoria(id);
            ra.addFlashAttribute("sucesso", "A Categoria foi excluída com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "A Categoria não foi encontrada.");
        }
        return new ModelAndView("redirect:/categoria/categorias");
    }

}
