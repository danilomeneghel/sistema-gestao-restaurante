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
import restaurante.model.Cardapio;
import restaurante.model.CardapioItem;
import restaurante.service.CardapioItemService;
import restaurante.service.CardapioService;
import restaurante.service.CategoriaService;
import restaurante.service.ProdutoService;

@Controller
@RequestMapping("/cardapio")
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;

    @Autowired
    private CardapioItemService cardapioItemService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/cardapios")
    public ModelAndView mostrarCardapios() {
        ModelAndView mv = new ModelAndView("cardapio/cardapios");
        mv.addObject("cardapios", cardapioService.findAllCardapios());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroCardapio() {
        ModelAndView mv = new ModelAndView("cardapio/cardapioCadastro");
        mv.addObject("cardapio", new Cardapio());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarCardapio(@Validated Cardapio cardapio, Errors errors) {
        ModelAndView mv = new ModelAndView("cardapio/cardapioCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Negócio foi cadastrado com sucesso!");
        cardapioService.salvarCardapio(cardapio);
        mv.addObject("cardapio", new Cardapio());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaCardapio(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("cardapio/cardapioEditar");
        mv.addObject("cardapio", cardapioService.findCardapioById(id));
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarCardapio(@Validated Cardapio cardapio, Errors errors) {
        ModelAndView mv = new ModelAndView("cardapio/cardapioEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Negócio foi atualizado com sucesso!");
        cardapioService.salvarCardapio(cardapio);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirCardapio(@PathVariable Long id, RedirectAttributes ra) {
        Cardapio cardapio = cardapioService.findCardapioById(id);
        if (cardapio != null) {
            cardapioService.excluirCardapio(id);
            ra.addFlashAttribute("sucesso", "O Negócio foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Negócio não foi encontrado.");
        }
        return new ModelAndView("redirect:/cardapio/cardapios");
    }

    @GetMapping("/cardapio-itens")
    public ModelAndView mostrarcardapioItens() {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItens");
        mv.addObject("cardapioItens", cardapioItemService.findAllCardapioItens());
        return mv;
    }

    @GetMapping("/cardapio-item/cadastro")
    public ModelAndView cadastroCardapioItem() {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemCadastro");
        mv.addObject("cardapioItem", new CardapioItem());
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("cardapios", cardapioService.findAllCardapios());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @PostMapping(value = "/cardapio-item/cadastrar", consumes = "multipart/form-data")
    public ModelAndView cadastrarCardapioItem(@Validated CardapioItem cardapioItem, Errors errors) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemCadastro");
        cardapioItemService.salvarCardapioItem(cardapioItem);
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("cardapioItem", new CardapioItem());
        mv.addObject("sucesso", "O Item do Cardápio foi cadastrado com sucesso!");
        return mv;
    }

    @GetMapping("/cardapio-item/editar/{id}")
    public ModelAndView editaCardapioItem(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemEditar");
        mv.addObject("cardapioItem", cardapioItemService.findCardapioItemById(id));
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("cardapios", cardapioService.findAllCardapios());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @PostMapping(value = "/cardapio-item/editar", consumes = "multipart/form-data")
    public ModelAndView editarCardapioItem(@Validated CardapioItem cardapioItem, Errors errors) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Item Do Cardápio foi atualizado com sucesso!");
        cardapioItem.setImagens(cardapioItemService.findCardapioItemById(cardapioItem.getId()).getImagens());
        cardapioItemService.salvarCardapioItem(cardapioItem);
        return mv;
    }

    @GetMapping("/cardapio-item/visualizar/{id}")
    public ModelAndView visualizarPedido(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemVisualizar");
        CardapioItem cardapioItem = cardapioItemService.findCardapioItemById(id);
        mv.addObject("cardapioItem", cardapioItem);
        return mv;
    }

    @GetMapping("/cardapio-item/excluir/{id}")
    public ModelAndView excluirCardapioItem(@PathVariable Long id, RedirectAttributes ra) {
        CardapioItem cardapioItem = cardapioItemService.findCardapioItemById(id);
        if (cardapioItem != null) {
            if (!cardapioService.findCardapioByNome(cardapioItem.getCardapio().getNome()).isEmpty()) {
                ra.addFlashAttribute("customMessage", "Não é possível excluir um Item do Cardápio " +
                        "com cardápio vinculado.");
            }
            cardapioItemService.excluirCardapioItem(id);
            ra.addFlashAttribute("sucesso", "O Item do Cardápio foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Item do Cardápio não foi encontrado.");
        }
        return new ModelAndView("redirect:/cardapio-item/cardapioItens");
    }

}
