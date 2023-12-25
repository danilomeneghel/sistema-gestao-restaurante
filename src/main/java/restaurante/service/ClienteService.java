package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.ClienteEntity;
import restaurante.model.Cliente;
import restaurante.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository rep;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Cliente> findAllClientes() {
        List<ClienteEntity> clientes = rep.findAll();
        return clientes.stream().map(entity -> modelMapper.map(entity, Cliente.class)).collect(Collectors.toList());
    }

    public Cliente findClienteById(Long id) {
        if (id != null) {
            ClienteEntity cliente = rep.findById(id).orElse(new ClienteEntity());
            return modelMapper.map(cliente, Cliente.class);
        }
        return null;
    }

    public Cliente findClienteByNome(String nome) {
        if (!nome.isEmpty()) {
            ClienteEntity cliente = rep.findByNome(nome).orElse(new ClienteEntity());
            return modelMapper.map(cliente, Cliente.class);
        }
        return null;
    }

    public Cliente findClienteByNomeIdNot(String nome, Long id) {
        if (!nome.isEmpty() && id != null) {
            ClienteEntity cliente = rep.findByNomeAndIdNot(nome, id).orElse(new ClienteEntity());
            return modelMapper.map(cliente, Cliente.class);
        }
        return null;
    }

    public List<Cliente> findClienteByNomeIgnoreCase(String nome) {
        List<ClienteEntity> clientes = rep.findByNomeContainingIgnoreCase(nome);
        return clientes.stream().map(entity -> modelMapper.map(entity, Cliente.class)).collect(Collectors.toList());
    }

    public Cliente salvarCliente(Cliente cliente) {
        ClienteEntity clienteEntity = modelMapper.map(cliente, ClienteEntity.class);
        ClienteEntity salvarCliente = rep.save(clienteEntity);
        return modelMapper.map(salvarCliente, Cliente.class);
    }

    public void excluirClienteById(Long id) {
        if (id != null) {
            rep.deleteById(id);
        }
    }

    public boolean emailExistente(String email) {
        if(!email.isEmpty()) {
            Optional<ClienteEntity> cliente = rep.findByEmail(email);
            return !cliente.isEmpty();
        }
        return false;
    }

}
