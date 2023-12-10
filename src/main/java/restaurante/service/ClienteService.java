package restaurante.service;

import restaurante.entity.ClienteEntity;
import restaurante.model.Cliente;
import restaurante.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<ClienteEntity> cliente = rep.findById(id);
        if (!cliente.isEmpty()) {
            return modelMapper.map(cliente.get(), Cliente.class);
        }
        return null;
    }

    public Cliente findClienteByNome(String nome) {
        Optional<ClienteEntity> cliente = rep.findByNome(nome);
        if (!cliente.isEmpty()) {
            return modelMapper.map(cliente.get(), Cliente.class);
        }
        return null;
    }

    public Cliente findClienteByNomeIdNot(String nome, Long id) {
        Optional<ClienteEntity> cliente = rep.findByNomeAndIdNot(nome, id);
        if (!cliente.isEmpty()) {
            return modelMapper.map(cliente.get(), Cliente.class);
        }
        return null;
    }

    public Cliente salvarCliente(Cliente cliente) {
        ClienteEntity clienteEntity = modelMapper.map(cliente, ClienteEntity.class);
        ClienteEntity salvarCliente = rep.save(clienteEntity);
        return modelMapper.map(salvarCliente, Cliente.class);
    }

    public void excluirClienteById(Long id) {
        rep.deleteById(id);
    }

    public boolean emailExistente(String email) {
        Optional<ClienteEntity> cliente = rep.findByEmail(email);
        return !cliente.isEmpty();
    }

}
