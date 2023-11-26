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
import restaurante.model.Fornecedor;
import restaurante.service.FornecedorService;

@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/fornecedores")
    public ModelAndView mostrarFornecedores() {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedores");
        mv.addObject("fornecedores", fornecedorService.findAllFornecedores());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroFornecedor() {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorCadastro");
        mv.addObject("fornecedor", new Fornecedor());
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarFornecedor(@Validated Fornecedor fornecedor, Errors errors) {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Fornecedor foi cadastrado com sucesso!");
        fornecedorService.salvarFornecedor(fornecedor);
        mv.addObject("fornecedor", new Fornecedor());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaFornecedor(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorEditar");
        Fornecedor fornecedor = fornecedorService.findFornecedorById(id);
        mv.addObject("fornecedor", fornecedor);
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarFornecedor(@Validated Fornecedor fornecedor, Errors errors) {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Fornecedor foi atualizado com sucesso!");
        fornecedorService.salvarFornecedor(fornecedor);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirFornecedor(@PathVariable Long id, RedirectAttributes ra) {
        Fornecedor fornecedor = fornecedorService.findFornecedorById(id);
        if (fornecedor != null) {
            fornecedorService.excluirFornecedorById(id);
            ra.addFlashAttribute("sucesso", "O Fornecedor foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Fornecedor não foi encontrado.");
        }
        return new ModelAndView("redirect:/fornecedor/todos");
    }

}
