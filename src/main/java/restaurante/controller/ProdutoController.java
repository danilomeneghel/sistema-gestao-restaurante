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
import restaurante.model.Produto;
import restaurante.service.CardapioItemService;
import restaurante.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CardapioItemService cardapioItemService;

    @GetMapping("/todos")
    public ModelAndView mostrarProdutos() {
        ModelAndView mv = new ModelAndView("produto/produtos");
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroProduto() {
        ModelAndView mv = new ModelAndView("produto/produtoCadastro");
        mv.addObject("produto", new Produto());
        return mv;
    }

    @PostMapping(value = "/cadastrar", consumes = "multipart/form-data")
    public ModelAndView cadastrarProduto(@Validated Produto produto, Errors errors) {
        ModelAndView mv = new ModelAndView("produto/produtoCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Produto foi cadastrado com sucesso!");
        produto.setImagens(produtoService.findProdutoById(produto.getId()).getImagens());
        produtoService.salvarProduto(produto);
        mv.addObject("produto", new Produto());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaProduto(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("produto/produtoEditar");
        mv.addObject("produto", produtoService.findProdutoById(id));
        return mv;
    }

    @PostMapping(value = "/editar", consumes = "multipart/form-data")
    public ModelAndView editarProduto(@Validated Produto produto, Errors errors) {
        ModelAndView mv = new ModelAndView("produto/produtoEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Produto foi atualizado com sucesso!");
        produto.setImagens(produtoService.findProdutoById(produto.getId()).getImagens());
        produtoService.salvarProduto(produto);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirProduto(@PathVariable Long id, RedirectAttributes ra) {
        Produto produto = produtoService.findProdutoById(id);
        if (produto != null) {
            if (!cardapioItemService.findCardapioItemByNome(produto.getCardapioItem().getNome()).isEmpty()) {
                ra.addFlashAttribute("customMessage", "Não é possível excluir um produto com " +
                        "items de cardápio vinculados.");
            }
            produtoService.excluirProduto(id);
            ra.addFlashAttribute("sucesso", "O Produto foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Produto não foi encontrado.");
        }
        return new ModelAndView("redirect:/classificador/produtos");
    }

}
