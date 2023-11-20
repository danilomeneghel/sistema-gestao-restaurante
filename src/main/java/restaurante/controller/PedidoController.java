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
import restaurante.model.Categoria;
import restaurante.model.Pedido;
import restaurante.service.CardapioItemService;
import restaurante.service.CategoriaService;
import restaurante.service.PedidoService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CardapioItemService cardapioItemService;

    @GetMapping("/pedidos-confirmados")
    public ModelAndView homePedidosUsuario() {
        ModelAndView mv = new ModelAndView("indexUsuario");
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("pedidos", pedidoService.findPedidosByConfirmado());
        return mv;
    }

    @GetMapping("/pedidos-confirmados/{idCategoria}")
    public ModelAndView filtrarPedidosUsuario(@PathVariable Long idCategoria) {
        ModelAndView mv = new ModelAndView("pedido/pedidosFiltro");
        if (idCategoria != 0) {
            Categoria categoria = categoriaService.findCategoriaById(idCategoria);
            mv.addObject("pedidos", pedidoService.findPedidosByConfirmadoAndCategoria(categoria));
        } else {
            mv.addObject("pedidos", pedidoService.findPedidosByConfirmado());
        }
        mv.addObject("categorias", categoriaService.findAllCategorias());
        return mv;
    }

    @GetMapping("/pedidos")
    public ModelAndView mostrarPedidos() {
        ModelAndView mv = new ModelAndView("pedido/pedidos");
        mv.addObject("pedido", pedidoService.findAllPedidos());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroPedido() {
        ModelAndView mv = new ModelAndView("pedido/pedidoCadastro");

        List<Categoria> categorias = categoriaService.findAllCategorias();
        List<CardapioItem> cardapioItens = cardapioItemService.findAllCardapioItens();

        addObj(mv);
        mv.addObject("categorias", categorias);
        mv.addObject("cardapioItens", cardapioItens);
        mv.addObject("pedido", new Pedido());
        return mv;
    }

    @PostMapping(value = "/cadastrar")
    public ModelAndView cadastrarPedido(@Validated Pedido pedido, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("pedido/pedidoCadastro");
        addObj(mv);
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();
        List<CardapioItem> cardapioItens = pedido.getCardapioItens();

        if (!cardapioItens.isEmpty()) {
            customMessage.add("Selecione um Item de Cardápio.");
            mv.addObject("erroCardapioItens", true);
            erro = true;
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        pedidoService.salvarPedido(pedido);

        mv.addObject("sucesso", "O Pedido foi cadastrado com sucesso.");
        mv.addObject("pedido", new Pedido());

        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaPedido(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("pedido/pedidoEditar");

        Pedido pedido = pedidoService.findPedidoById(id);
        List<CardapioItem> cardapioItens = pedido.getCardapioItens();

        addObj(mv);
        mv.addObject("pedido", pedido);
        mv.addObject("cardapioItens", cardapioItens);
        return mv;
    }

    @PostMapping(value = "/editar")
    public ModelAndView editarPedido(@Validated Pedido pedido, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("pedido/pedidoEditar");
        boolean erro = false;
        addObj(mv);

        List<String> customMessage = new ArrayList<String>();
        List<CardapioItem> cardapioItens = pedido.getCardapioItens();

        if (!cardapioItens.isEmpty()) {
            customMessage.add("Selecione um Item de Cardápio.");
            mv.addObject("erroCardapioItens", true);
            erro = true;
        }
        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        pedidoService.salvarPedido(pedido);
        mv.addObject("sucesso", "O pedido foi atualizado com sucesso!");
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirPedido(@PathVariable Long id, RedirectAttributes ra) {
        Pedido pedido = pedidoService.findPedidoById(id);
        if (pedido != null) {
            pedidoService.excluirPedidoById(id);
            ra.addFlashAttribute("sucesso", "O Pedido foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Pedido não foi encontrado.");
        }
        return new ModelAndView("redirect:/pedido/pedidos");
    }

    private void addObj(ModelAndView mv) {
        mv.addObject("pedidos", pedidoService.findAllPedidos());
        mv.addObject("categorias", categoriaService.findAllCategorias());
    }

}
