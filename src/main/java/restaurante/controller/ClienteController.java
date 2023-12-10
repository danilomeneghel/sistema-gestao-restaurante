package restaurante.controller;

import restaurante.enums.Ativo;
import restaurante.model.Bairro;
import restaurante.model.Cliente;
import restaurante.model.Estado;
import restaurante.model.Municipio;
import restaurante.service.ClienteService;
import restaurante.service.LocalidadeService;
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
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping("/clientes")
    public ModelAndView mostrarClientes() {
        ModelAndView mv = new ModelAndView("cliente/clientes");
        mv.addObject("clientes", clienteService.findAllClientes());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroCliente() {
        ModelAndView mv = new ModelAndView("cliente/clienteCadastro");
        List<Bairro> bairros = localidadeService.findAllBairros();
        List<Municipio> municipios = localidadeService.findAllMunicipios();
        List<Estado> estados = localidadeService.findAllEstados();

        mv.addObject("cliente", new Cliente());
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("estados", estados);
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarCliente(@Validated Cliente cliente, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("cliente/clienteCadastro");
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();
        List<Estado> estados = localidadeService.findAllEstados();
        List<Municipio> municipios = null;
        List<Bairro> bairros = null;

        mv.addObject("estados", estados);

        Cliente estab = clienteService.findClienteByNome(cliente.getNome());

        if (estab != null) {
            customMessage.add("Nome do Cliente já cadastrado.");
            mv.addObject("erroCliente", true);
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

        if (cliente.getBairro().getId() == null) {
            customMessage.add("O Bairro selecionado deve ser válido.");
            mv.addObject("erroBairro", true);
            bairros = localidadeService.findAllBairros();
            erro = true;
        } else {
            bairros = localidadeService.findBairroPerMunicipio(idMunicipio);
            Bairro bairro = localidadeService.findBairroById(cliente.getBairro().getId());
            cliente.setMunicipio(bairro.getMunicipio());
            cliente.setEstado(bairro.getMunicipio().getEstado());
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }

        clienteService.salvarCliente(cliente);

        mv.addObject("cliente", new Cliente());
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("idEstado", idEstado);
        mv.addObject("idMunicipio", idMunicipio);
        mv.addObject("idBairro", cliente.getBairro().getId());
        mv.addObject("sucesso", "O Cliente foi cadastrado com sucesso!");
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaCliente(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("cliente/clienteEditar");
        Cliente cliente = clienteService.findClienteById(id);
        List<Bairro> bairros = localidadeService.findBairroPerMunicipio(cliente.getBairro().getMunicipio().getId());
        List<Municipio> municipios = localidadeService.findMunicipioPerEstado(cliente.getBairro().getMunicipio().getEstado().getId());
        List<Estado> estados = localidadeService.findAllEstados();

        mv.addObject("cliente", cliente);
        mv.addObject("bairros", bairros);
        mv.addObject("idBairro", cliente.getBairro().getId());
        mv.addObject("municipios", municipios);
        mv.addObject("idMunicipio", cliente.getBairro().getMunicipio().getId());
        mv.addObject("estados", estados);
        mv.addObject("idEstado", cliente.getBairro().getMunicipio().getEstado().getId());
        mv.addObject("ativo", Ativo.values());
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarCliente(@Validated Cliente cliente, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("cliente/clienteEditar");
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();
        List<Estado> estados = localidadeService.findAllEstados();
        List<Municipio> municipios = null;
        List<Bairro> bairros = null;

        mv.addObject("estados", estados);

        Cliente estab = clienteService.findClienteByNomeIdNot(cliente.getNome(), cliente.getId());

        if (estab != null) {
            customMessage.add("Nome do Cliente já cadastrado.");
            mv.addObject("erroCliente", true);
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

        if (cliente.getBairro().getId() == null) {
            customMessage.add("O Bairro selecionado deve ser válido.");
            mv.addObject("erroBairro", true);
            bairros = localidadeService.findAllBairros();
            erro = true;
        } else {
            bairros = localidadeService.findBairroPerMunicipio(idMunicipio);
            Bairro bairro = localidadeService.findBairroById(cliente.getBairro().getId());
            cliente.setMunicipio(bairro.getMunicipio());
            cliente.setEstado(bairro.getMunicipio().getEstado());
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }

        clienteService.salvarCliente(cliente);

        mv.addObject("cliente", cliente);
        mv.addObject("bairros", bairros);
        mv.addObject("municipios", municipios);
        mv.addObject("idEstado", idEstado);
        mv.addObject("idMunicipio", idMunicipio);
        mv.addObject("idBairro", cliente.getBairro().getId());
        mv.addObject("sucesso", "O Cliente foi atualizado com sucesso!");
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirCliente(@PathVariable Long id, RedirectAttributes ra) {
        Cliente cliente = clienteService.findClienteById(id);
        if (cliente != null) {
            clienteService.excluirClienteById(id);
            ra.addFlashAttribute("sucesso", "O Cliente foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Cliente não foi encontrado.");
        }
        return new ModelAndView("redirect:/cliente/clientes");
    }

}
