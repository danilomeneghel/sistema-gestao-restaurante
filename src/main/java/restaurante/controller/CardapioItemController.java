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
import restaurante.model.CardapioItem;
import restaurante.service.CardapioItemService;
import restaurante.service.CardapioService;

@Controller
@RequestMapping("/cardapio-item")
public class CardapioItemController {

    @Autowired
    private CardapioItemService cardapioItemService;

    @Autowired
    private CardapioService cardapioService;

    @GetMapping("/todos")
    public ModelAndView mostrarCardapioItems() {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItems");
        mv.addObject("cardapioItems", cardapioItemService.findAllCardapioItems());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroCardapioItem() {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemCadastro");
        mv.addObject("cardapioItem", new CardapioItem());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarCardapioItem(@Validated CardapioItem cardapioItem, Errors errors) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Item do Cardápio foi cadastrado com sucesso!");
        cardapioItemService.salvarCardapioItem(cardapioItem);
        mv.addObject("cardapioItem", new CardapioItem());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaCardapioItem(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemEditar");
        mv.addObject("cardapioItem", cardapioItemService.findCardapioItemById(id));
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarCardapioItem(@Validated CardapioItem cardapioItem, Errors errors) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Item Do Cardápio foi atualizado com sucesso!");
        cardapioItemService.salvarCardapioItem(cardapioItem);
        return mv;
    }

    @GetMapping("/excluir/{id}")
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
        return new ModelAndView("redirect:/cardapio-item/cardapioItems");
    }

}
