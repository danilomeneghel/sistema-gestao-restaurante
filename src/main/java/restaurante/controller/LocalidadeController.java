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
import restaurante.model.Bairro;
import restaurante.model.Estado;
import restaurante.model.Municipio;
import restaurante.service.LocalidadeService;
import restaurante.service.PedidoService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/localidade")
public class LocalidadeController {

    @Autowired
    private LocalidadeService localidadeService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/estados")
    public ModelAndView mostrarEstados() {
        ModelAndView mv = new ModelAndView("estado/estados");
        mv.addObject("estados", localidadeService.findAllEstados());
        return mv;
    }

    @GetMapping("/estado/cadastro")
    public ModelAndView cadastroEstados() {
        ModelAndView mv = new ModelAndView("estado/estadoCadastro");
        mv.addObject("estado", new Estado());
        return mv;
    }

    @PostMapping("/estado/cadastro-preenchido")
    public ModelAndView cadastrarEstado(@Validated Estado estado, Errors errors) {
        ModelAndView mv = new ModelAndView("estado/estadoCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Estado Foi Cadastrado com Sucesso!");
        localidadeService.salvarEstado(estado);
        mv.addObject("estado", new Estado());
        return mv;
    }

    @GetMapping("/estado/editar/{id}")
    public ModelAndView editaEstado(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("estado/estadoEditar");
        mv.addObject("estado", localidadeService.findEstadoById(id));
        return mv;
    }

    @PostMapping("/estado/editar")
    public ModelAndView editarEstado(@Validated Estado estado, Errors errors) {
        ModelAndView mv = new ModelAndView("estado/estadoEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Estado foi atualizado com sucesso!");
        localidadeService.salvarEstado(estado);
        return mv;
    }

    @GetMapping("/estado/excluir/{id}")
    public ModelAndView excluirEstado(@PathVariable Long id, RedirectAttributes ra) {
        if (localidadeService.findMunicipioPerEstado(id) != null) {
            ra.addFlashAttribute("customMessage", "Não é possível excluir um Estado com municípios vinculados.");
            return new ModelAndView("redirect:/localidade/estados");
        }
        localidadeService.excluirEstadoById(id);
        ra.addFlashAttribute("sucesso", "O Estado foi excluído com sucesso");
        return new ModelAndView("redirect:/localidade/estados");
    }

    @GetMapping("/municipios")
    public ModelAndView mostrarMunicipios() {
        ModelAndView mv = new ModelAndView("municipio/municipios");
        mv.addObject("municipios", localidadeService.findAllMunicipios());
        return mv;
    }

    @GetMapping("/municipio/cadastro")
    public ModelAndView cadastroMunicipio() {
        ModelAndView mv = new ModelAndView("municipio/municipioCadastro");
        Municipio mp = new Municipio();
        Estado et = new Estado();
        mv.addObject("municipio", mp);
        mv.addObject("estados", localidadeService.findAllEstados());
        return mv;
    }

    @PostMapping("/municipio/cadastrar")
    public ModelAndView cadastrarMunicipio(@Validated Municipio municipio, Errors errors) {
        ModelAndView mv = new ModelAndView("municipio/municipioCadastro");
        mv.addObject("estados", localidadeService.findAllEstados());
        Estado estado = localidadeService.findEstadoByMunicipio(municipio);
        if (errors.hasErrors() || estado == null) {
            mv.addObject("customMessage", "O Estado escolhido deve ser válido.");
            return mv;
        }
        mv.addObject("sucesso", "O Município foi cadastrado com sucesso!");

        localidadeService.salvarMunicipio(municipio);
        mv.addObject("municipio", new Municipio());
        return mv;
    }

    @GetMapping("/municipio/editar/{id}")
    public ModelAndView editaMunicipio(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("municipio/municipioEditar");
        mv.addObject("estados", localidadeService.findAllEstados());
        mv.addObject("municipio", localidadeService.findMunicipioById(id));
        return mv;
    }

    @PostMapping("/municipio/editar")
    public ModelAndView editarMunicipio(@Validated Municipio municipio, Errors errors) {
        ModelAndView mv = new ModelAndView("municipio/municipioEditar");
        mv.addObject("estados", localidadeService.findAllEstados());
        Estado estado = localidadeService.findEstadoByMunicipio(municipio);
        if (errors.hasErrors() || estado == null) {
            mv.addObject("customMessage", "O Estado escolhido deve ser válido.");
            return mv;
        }

        localidadeService.salvarMunicipio(municipio);
        mv.addObject("sucesso", "O Município foi atualizado com sucesso!");
        return mv;
    }

    @GetMapping("/municipio/excluir/{id}")
    public ModelAndView excluirMunicipio(@PathVariable Long id, RedirectAttributes ra) {
        if (localidadeService.findBairroPerMunicipio(id) != null) {
            ra.addFlashAttribute("customMessage", "Não é possível excluir um Município com bairros vinculados.");
            return new ModelAndView("redirect:/localidade/municipios");
        }

        localidadeService.excluirMunicipioById(id);
        ra.addFlashAttribute("sucesso", "O município foi excluído com sucesso");
        return new ModelAndView("redirect:/localidade/municipios");
    }

    @GetMapping("/bairros")
    public ModelAndView mostrarBairros() {
        ModelAndView mv = new ModelAndView("bairro/bairros");
        mv.addObject("bairros", localidadeService.findAllBairros());
        return mv;
    }

    @GetMapping("/bairro/cadastro")
    public ModelAndView cadastroBairro() {
        ModelAndView mv = new ModelAndView("bairro/bairroCadastro");
        mv.addObject("bairro", new Bairro());
        mv.addObject("idEstado", null);
        mv.addObject("estados", localidadeService.findAllEstados());
        mv.addObject("municipios", localidadeService.findAllMunicipios());
        return mv;
    }

    @PostMapping("/bairro/cadastrar")
    public ModelAndView cadastrarBairro(@Validated Bairro bairro, Errors errors, Integer idEstado) {
        ModelAndView mv = new ModelAndView("bairro/bairroCadastro");
        mv.addObject("estados", localidadeService.findAllEstados());
        mv.addObject("municipios", localidadeService.findAllMunicipios());

        Municipio municipio = localidadeService.findMunicipioByBairro(bairro);
        List<String> customMessage = new ArrayList<String>();
        boolean erro = false;

        if (municipio == null) {
            customMessage.add("O Municipio escolhido deve ser válido.");
            mv.addObject("municipio_ok", true);
            erro = true;
        }
        if (idEstado == null) {
            customMessage.add("O Estado escolhido deve ser válido.");
            mv.addObject("estado_ok", true);
            erro = true;
        }
        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        mv.addObject("sucesso", "O Bairro foi cadastrado com sucesso!");
        localidadeService.salvarBairro(bairro);
        mv.addObject("bairro", new Bairro());
        return mv;
    }

    @GetMapping("/bairro/editar/{id}")
    public ModelAndView editaBairro(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("bairro/bairroEditar");
        Bairro bairro = localidadeService.findBairroById(id);
        mv.addObject("bairro", bairro);
        mv.addObject("estados", localidadeService.findAllEstados());
        mv.addObject("municipios", localidadeService.findMunicipioByBairro(bairro));
        return mv;
    }

    @PostMapping("/bairro/editar")
    public ModelAndView editarBairro(@Validated Bairro bairro, Errors errors) {
        ModelAndView mv = new ModelAndView("bairro/bairroEditar");
        mv.addObject("estados", localidadeService.findAllEstados());
        mv.addObject("municipios", localidadeService.findAllMunicipios());

        List<String> customMessage = new ArrayList<String>();
        boolean erro = false;

        Municipio municipio = localidadeService.findMunicipioByBairro(bairro);

        if (municipio == null) {
            customMessage.add("O Municipio escolhido deve ser válido.");
            mv.addObject("municipio_ok", true);
            erro = true;
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        mv.addObject("sucesso", "O Bairro foi atualizado com sucesso!");

        localidadeService.salvarBairro(bairro);
        mv.addObject("municipios", localidadeService.findMunicipioByBairro(bairro));
        return mv;
    }

    @GetMapping("/bairro/excluir/{id}")
    public ModelAndView excluirBairro(@PathVariable Long id, RedirectAttributes ra) {
        if (localidadeService.findBairroById(id) == null) {
            ra.addFlashAttribute("customMessage", "Não é possível excluir um bairro com " +
                    "restaurantes vinculados.");
            return new ModelAndView("redirect:/localidade/bairros");
        }
        localidadeService.excluirBairroById(id);
        ra.addFlashAttribute("sucesso", "O bairro foi excluído com sucesso");
        return new ModelAndView("redirect:/local/bairros");
    }

}
