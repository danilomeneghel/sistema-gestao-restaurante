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

@Controller
@RequestMapping("/cardapio")
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;

    @Autowired
    private CardapioItemService cardapioItemService;

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
            if (!cardapioItemService.findCardapioItemByNome(cardapio.getCardapioItems().get(0).getNome()).isEmpty()) {
                ra.addFlashAttribute("customMessage", "Não é possível excluir um cardápio com " +
                        "items de cardápio vinculados.");
            }
            cardapioService.excluirCardapio(id);
            ra.addFlashAttribute("sucesso", "O Negócio foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Negócio não foi encontrado.");
        }
        return new ModelAndView("redirect:/cardapio/cardapios");
    }

    @GetMapping("/cardapio-item/todos")
    public ModelAndView mostrarCardapioItems() {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItems");
        mv.addObject("cardapioItems", cardapioItemService.findAllCardapioItems());
        return mv;
    }

    @GetMapping("/cardapio-item/cadastro")
    public ModelAndView cadastroCardapioItem() {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemCadastro");
        mv.addObject("cardapioItem", new CardapioItem());
        return mv;
    }

    @PostMapping("/cardapio-item/cadastrar")
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

    @GetMapping("/cardapio-item/editar/{id}")
    public ModelAndView editaCardapioItem(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemEditar");
        mv.addObject("cardapioItem", cardapioItemService.findCardapioItemById(id));
        return mv;
    }

    @PostMapping("/cardapio-item/editar")
    public ModelAndView editarCardapioItem(@Validated CardapioItem cardapioItem, Errors errors) {
        ModelAndView mv = new ModelAndView("cardapio-item/cardapioItemEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Item Do Cardápio foi atualizado com sucesso!");
        cardapioItemService.salvarCardapioItem(cardapioItem);
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
        return new ModelAndView("redirect:/cardapio-item/cardapioItems");
    }

}
