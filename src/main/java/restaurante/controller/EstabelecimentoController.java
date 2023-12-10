package restaurante.controller;

import loja.enums.Ativo;
import loja.model.Bairro;
import loja.model.Estabelecimento;
import loja.model.Estado;
import loja.model.Municipio;
import loja.service.EstabelecimentoService;
import loja.service.LocalidadeService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping("/estabelecimentos")
    public ModelAndView mostrarEstabelecimentos() {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentos");
        mv.addObject("estabelecimentos", estabelecimentoService.findAllEstabelecimentos());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroEstabelecimento() {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoCadastro");
        List<Bairro> bairros = localidadeService.findAllBairros();
        List<Municipio> municipios = localidadeService.findAllMunicipios();
        List<Estado> estados = localidadeService.findAllEstados();

        mv.addObject("estabelecimento", new Estabelecimento());
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("estados", estados);
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarEstabelecimento(@Validated Estabelecimento estabelecimento, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoCadastro");
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();
        List<Estado> estados = localidadeService.findAllEstados();
        List<Municipio> municipios = null;
        List<Bairro> bairros = null;

        mv.addObject("estados", estados);

        Estabelecimento estab = estabelecimentoService.findEstabelecimentoByNome(estabelecimento.getNome());

        if (estab != null) {
            customMessage.add("Nome de Estabelecimento já cadastrado.");
            mv.addObject("erroEstabelecimento", true);
            erro = true;
        }

        if (idEstado == null) {
            customMessage.add("O Estado selecionado deve ser válido.");
            mv.addObject("erroEstado", true);
            erro = true;
        }

        if (idMunicipio == null) {
            customMessage.add("O Municipio selecionado deve ser válido.");
            mv.addObject("erroMunicipio", true);
            municipios = localidadeService.findAllMunicipios();
            erro = true;
        } else {
            municipios = localidadeService.findMunicipioPerEstado(idEstado);
        }

        if (estabelecimento.getBairro().getId() == null) {
            customMessage.add("O Bairro selecionado deve ser válido.");
            mv.addObject("erroBairro", true);
            bairros = localidadeService.findAllBairros();
            erro = true;
        } else {
            bairros = localidadeService.findBairroPerMunicipio(idMunicipio);
            Bairro bairro = localidadeService.findBairroById(estabelecimento.getBairro().getId());
            estabelecimento.setMunicipio(bairro.getMunicipio());
            estabelecimento.setEstado(bairro.getMunicipio().getEstado());
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }

        estabelecimentoService.salvarEstabelecimento(estabelecimento);

        mv.addObject("estabelecimento", new Estabelecimento());
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("idEstado", idEstado);
        mv.addObject("idMunicipio", idMunicipio);
        mv.addObject("idBairro", estabelecimento.getBairro().getId());
        mv.addObject("sucesso", "O Estabelecimento foi cadastrado com sucesso!");
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaEstabelecimento(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoEditar");
        Estabelecimento estabelecimento = estabelecimentoService.findEstabelecimentoById(id);
        List<Bairro> bairros = localidadeService.findBairroPerMunicipio(estabelecimento.getBairro().getMunicipio().getId());
        List<Municipio> municipios = localidadeService.findMunicipioPerEstado(estabelecimento.getBairro().getMunicipio().getEstado().getId());
        List<Estado> estados = localidadeService.findAllEstados();

        mv.addObject("estabelecimento", estabelecimento);
        mv.addObject("bairros", bairros);
        mv.addObject("idBairro", estabelecimento.getBairro().getId());
        mv.addObject("municipios", municipios);
        mv.addObject("idMunicipio", estabelecimento.getBairro().getMunicipio().getId());
        mv.addObject("estados", estados);
        mv.addObject("idEstado", estabelecimento.getBairro().getMunicipio().getEstado().getId());
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarEstabelecimento(@Validated Estabelecimento estabelecimento, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("estabelecimento/estabelecimentoEditar");
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();
        List<Estado> estados = localidadeService.findAllEstados();
        List<Municipio> municipios = null;
        List<Bairro> bairros = null;

        mv.addObject("estados", estados);

        Estabelecimento estab = estabelecimentoService.findEstabelecimentoByNome(estabelecimento.getNome());

        if (estab != null) {
            customMessage.add("Nome de Estabelecimento já cadastrado.");
            mv.addObject("erroEstabelecimento", true);
            erro = true;
        }

        if (idEstado == null) {
            customMessage.add("O Estado selecionado deve ser válido.");
            mv.addObject("erroEstado", true);
            erro = true;
        }

        if (idMunicipio == null) {
            customMessage.add("O Municipio selecionado deve ser válido.");
            mv.addObject("erroMunicipio", true);
            municipios = localidadeService.findAllMunicipios();
            erro = true;
        } else {
            municipios = localidadeService.findMunicipioPerEstado(idEstado);
        }

        if (estabelecimento.getBairro().getId() == null) {
            customMessage.add("O Bairro selecionado deve ser válido.");
            mv.addObject("erroBairro", true);
            bairros = localidadeService.findAllBairros();
            erro = true;
        } else {
            bairros = localidadeService.findBairroPerMunicipio(idMunicipio);
            Bairro bairro = localidadeService.findBairroById(estabelecimento.getBairro().getId());
            estabelecimento.setMunicipio(bairro.getMunicipio());
            estabelecimento.setEstado(bairro.getMunicipio().getEstado());
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }

        estabelecimentoService.salvarEstabelecimento(estabelecimento);

        mv.addObject("estabelecimento", estabelecimento);
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("idEstado", idEstado);
        mv.addObject("idMunicipio", idMunicipio);
        mv.addObject("idBairro", estabelecimento.getBairro().getId());
        mv.addObject("sucesso", "O Estabelecimento foi atualizado com sucesso!");
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
        return new ModelAndView("redirect:/estabelecimento/estabelecimentos");
    }

}
