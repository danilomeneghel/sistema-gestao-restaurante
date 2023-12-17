package restaurante.service;

import restaurante.entity.FornecedorEntity;
import restaurante.model.Fornecedor;
import restaurante.repository.FornecedorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findById(id);
        if (!fornecedor.isEmpty()) {
            return modelMapper.map(fornecedor.get(), Fornecedor.class);
        }
        return null;
    }

    public Fornecedor findFornecedorByNome(String nome) {
        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findByNome(nome);
        if (!fornecedor.isEmpty()) {
            return modelMapper.map(fornecedor.get(), Fornecedor.class);
        }
        return null;
    }

    public Fornecedor findFornecedorByNomeIdNot(String nome, Long id) {
        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findByNomeAndIdNot(nome, id);
        if (!fornecedor.isEmpty()) {
            return modelMapper.map(fornecedor.get(), Fornecedor.class);
        }
        return null;
    }

    public List<Fornecedor> findFornecedorByNomeIgnoreCase(String nome) {
        List<FornecedorEntity> fornecedores = fornecedorRepository.findByNomeContainingIgnoreCase(nome);
        return fornecedores.stream().map(entity -> modelMapper.map(entity, Fornecedor.class)).collect(Collectors.toList());
    }

    public Fornecedor salvarFornecedor(Fornecedor fornecedor) {
        FornecedorEntity fornecedorEntity = modelMapper.map(fornecedor, FornecedorEntity.class);
        FornecedorEntity salvarFornecedor = fornecedorRepository.save(fornecedorEntity);
        return modelMapper.map(salvarFornecedor, Fornecedor.class);
    }

    public void excluirFornecedorById(Long id) {
        fornecedorRepository.deleteById(id);
    }

    public boolean emailExistente(String email) {
        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findByEmail(email);
        return !fornecedor.isEmpty();
    }

}
