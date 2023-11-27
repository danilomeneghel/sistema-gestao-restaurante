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
import restaurante.model.Estabelecimento;
import restaurante.service.EstabelecimentoService;

@Controller
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping("/estabelecimentos")
    public ModelAndView mostrarEstabelecimentos() {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentos");
        mv.addObject("estabelecimentos", estabelecimentoService.findAllEstabelecimentos());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroEstabelecimento() {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoCadastro");
        mv.addObject("estabelecimento", new Estabelecimento());
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarEstabelecimento(@Validated Estabelecimento estabelecimento, Errors errors) {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Estabelecimento foi cadastrado com sucesso!");
        estabelecimentoService.salvarEstabelecimento(estabelecimento);
        mv.addObject("estabelecimento", new Estabelecimento());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaEstabelecimento(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoEditar");
        Estabelecimento estabelecimento = estabelecimentoService.findEstabelecimentoById(id);
        mv.addObject("estabelecimento", estabelecimento);
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarEstabelecimento(@Validated Estabelecimento estabelecimento, Errors errors) {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Estabelecimento foi atualizado com sucesso!");
        estabelecimentoService.salvarEstabelecimento(estabelecimento);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirEstabelecimento(@PathVariable Long id, RedirectAttributes ra) {
        Estabelecimento estabelecimento = estabelecimentoService.findEstabelecimentoById(id);
        if (estabelecimento != null) {
            estabelecimentoService.excluirEstabelecimentoById(id);
            ra.addFlashAttribute("sucesso", "O Estabelecimento foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Estabelecimento não foi encontrado.");
        }
        return new ModelAndView("redirect:/estabelecimento/todos");
    }

}