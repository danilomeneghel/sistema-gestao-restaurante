package restaurante.service;

import loja.entity.FornecedorEntity;
import loja.model.Fornecedor;
import loja.repository.FornecedorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository rep;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Fornecedor> findAllFornecedores() {
        List<FornecedorEntity> fornecedors = rep.findAll();
        return fornecedors.stream().map(entity -> modelMapper.map(entity, Fornecedor.class)).collect(Collectors.toList());
    }

    public Fornecedor findFornecedorById(Long id) {
        Optional<FornecedorEntity> fornecedor = rep.findById(id);
        if (!fornecedor.isEmpty()) {
            return modelMapper.map(fornecedor.get(), Fornecedor.class);
        }
        return null;
    }

    public Fornecedor findFornecedorByNome(String nome) {
        Optional<FornecedorEntity> fornecedor = rep.findByNome(nome);
        if (!fornecedor.isEmpty()) {
            return modelMapper.map(fornecedor.get(), Fornecedor.class);
        }
        return null;
    }

    public Fornecedor salvarFornecedor(Fornecedor fornecedor) {
        FornecedorEntity fornecedorEntity = modelMapper.map(fornecedor, FornecedorEntity.class);
        FornecedorEntity salvarFornecedor = rep.save(fornecedorEntity);
        return modelMapper.map(salvarFornecedor, Fornecedor.class);
    }

    public void excluirFornecedorById(Long id) {
        rep.deleteById(id);
    }

    public boolean emailExistente(String email) {
        Optional<FornecedorEntity> fornecedor = rep.findByEmail(email);
        return !fornecedor.isEmpty();
    }

}
