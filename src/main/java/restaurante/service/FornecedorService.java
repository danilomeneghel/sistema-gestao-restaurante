package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.FornecedorEntity;
import restaurante.model.Fornecedor;
import restaurante.repository.FornecedorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Fornecedor> findAllFornecedores() {
        List<FornecedorEntity> fornecedors = fornecedorRepository.findAll();
        return fornecedors.stream().map(entity -> modelMapper.map(entity, Fornecedor.class)).collect(Collectors.toList());
    }

    public Fornecedor findFornecedorById(Long id) {
        if (id != null) {
            FornecedorEntity fornecedor = fornecedorRepository.findById(id).orElse(new FornecedorEntity());
            return modelMapper.map(fornecedor, Fornecedor.class);
        }
        return null;
    }

    public Fornecedor findFornecedorByNome(String nome) {
        if (!nome.isEmpty()) {
            FornecedorEntity fornecedor = fornecedorRepository.findByNome(nome).orElse(new FornecedorEntity());
            return modelMapper.map(fornecedor, Fornecedor.class);
        }
        return null;
    }

    public Fornecedor findFornecedorByNomeIdNot(String nome, Long id) {
        if (!nome.isEmpty() && id != null) {
            FornecedorEntity fornecedor = fornecedorRepository.findByNomeAndIdNot(nome, id).orElse(new FornecedorEntity());
            return modelMapper.map(fornecedor, Fornecedor.class);
        }
        return null;
    }

    public List<Fornecedor> findFornecedorByNomeIgnoreCase(String nome) {
        List<FornecedorEntity> fornecedors = fornecedorRepository.findByNomeContainingIgnoreCase(nome);
        return fornecedors.stream().map(entity -> modelMapper.map(entity, Fornecedor.class)).collect(Collectors.toList());
    }

    public Fornecedor salvarFornecedor(Fornecedor fornecedor) {
        FornecedorEntity fornecedorEntity = modelMapper.map(fornecedor, FornecedorEntity.class);
        FornecedorEntity salvarFornecedor = fornecedorRepository.save(fornecedorEntity);
        return modelMapper.map(salvarFornecedor, Fornecedor.class);
    }

    public void excluirFornecedorById(Long id) {
        if (id != null) {
            fornecedorRepository.deleteById(id);
        }
    }

    public boolean emailExistente(String email) {
        if (!email.isEmpty()) {
            Optional<FornecedorEntity> fornecedor = fornecedorRepository.findByEmail(email);
            return !fornecedor.isEmpty();
        }
        return false;
    }

}
