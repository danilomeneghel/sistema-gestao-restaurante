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
import restaurante.model.Bairro;
import restaurante.model.Estado;
import restaurante.model.Fornecedor;
import restaurante.model.Municipio;
import restaurante.service.FornecedorService;
import restaurante.service.LocalidadeService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping("/fornecedores")
    public ModelAndView mostrarFornecedores() {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedores");
        mv.addObject("fornecedores", fornecedorService.findAllFornecedores());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroFornecedor() {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorCadastro");
        List<Bairro> bairros = localidadeService.findAllBairros();
        List<Municipio> municipios = localidadeService.findAllMunicipios();
        List<Estado> estados = localidadeService.findAllEstados();

        mv.addObject("fornecedor", new Fornecedor());
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("estados", estados);
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarFornecedor(@Validated Fornecedor fornecedor, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorCadastro");
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();
        List<Estado> estados = localidadeService.findAllEstados();
        List<Municipio> municipios = null;
        List<Bairro> bairros = null;

        mv.addObject("estados", estados);

        Fornecedor fornec = fornecedorService.findFornecedorByNome(fornecedor.getNome());

        if (fornec.getNome() != null) {
            customMessage.add("Nome do Fornecedor já cadastrado.");
            mv.addObject("erroFornecedor", true);
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

        if (fornecedor.getBairro().getId() == null) {
            customMessage.add("O Bairro selecionado deve ser válido.");
            mv.addObject("erroBairro", true);
            bairros = localidadeService.findAllBairros();
            erro = true;
        } else {
            bairros = localidadeService.findBairroPerMunicipio(idMunicipio);
            Bairro bairro = localidadeService.findBairroById(fornecedor.getBairro().getId());
            fornecedor.setMunicipio(bairro.getMunicipio());
            fornecedor.setEstado(bairro.getMunicipio().getEstado());
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }

        fornecedorService.salvarFornecedor(fornecedor);

        mv.addObject("fornecedor", new Fornecedor());
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("idEstado", idEstado);
        mv.addObject("idMunicipio", idMunicipio);
        mv.addObject("idBairro", fornecedor.getBairro().getId());
        mv.addObject("sucesso", "O Fornecedor foi cadastrado com sucesso!");
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaFornecedor(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorEditar");
        Fornecedor fornecedor = fornecedorService.findFornecedorById(id);
        List<Bairro> bairros = localidadeService.findBairroPerMunicipio(fornecedor.getBairro().getMunicipio().getId());
        List<Municipio> municipios = localidadeService.findMunicipioPerEstado(fornecedor.getBairro().getMunicipio().getEstado().getId());
        List<Estado> estados = localidadeService.findAllEstados();

        mv.addObject("fornecedor", fornecedor);
        mv.addObject("bairros", bairros);
        mv.addObject("idBairro", fornecedor.getBairro().getId());
        mv.addObject("municipios", municipios);
        mv.addObject("idMunicipio", fornecedor.getBairro().getMunicipio().getId());
        mv.addObject("estados", estados);
        mv.addObject("idEstado", fornecedor.getBairro().getMunicipio().getEstado().getId());
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarFornecedor(@Validated Fornecedor fornecedor, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("fornecedor/fornecedorEditar");
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();
        List<Estado> estados = localidadeService.findAllEstados();
        List<Municipio> municipios = null;
        List<Bairro> bairros = null;

        mv.addObject("estados", estados);

        Fornecedor fornec = fornecedorService.findFornecedorByNomeIdNot(fornecedor.getNome(), fornecedor.getId());

        if (fornec != null) {
            customMessage.add("Nome do Fornecedor já cadastrado.");
            mv.addObject("erroFornecedor", true);
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

        if (fornecedor.getBairro().getId() == null) {
            customMessage.add("O Bairro selecionado deve ser válido.");
            mv.addObject("erroBairro", true);
            bairros = localidadeService.findAllBairros();
            erro = true;
        } else {
            bairros = localidadeService.findBairroPerMunicipio(idMunicipio);
            Bairro bairro = localidadeService.findBairroById(fornecedor.getBairro().getId());
            fornecedor.setMunicipio(bairro.getMunicipio());
            fornecedor.setEstado(bairro.getMunicipio().getEstado());
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }

        fornecedorService.salvarFornecedor(fornecedor);

        mv.addObject("fornecedor", fornecedor);
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("idEstado", idEstado);
        mv.addObject("idMunicipio", idMunicipio);
        mv.addObject("idBairro", fornecedor.getBairro().getId());
        mv.addObject("sucesso", "O Fornecedor foi atualizado com sucesso!");
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirFornecedor(@PathVariable Long id, RedirectAttributes ra) {
        if (fornecedorService.findFornecedorById(id) != null) {
            fornecedorService.excluirFornecedorById(id);
            ra.addFlashAttribute("sucesso", "O Fornecedor foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Fornecedor não foi encontrado.");
        }
        return new ModelAndView("redirect:/fornecedor/fornecedores");
    }

}
