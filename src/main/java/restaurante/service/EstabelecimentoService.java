package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.EstabelecimentoEntity;
import restaurante.model.Estabelecimento;
import restaurante.repository.EstabelecimentoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Estabelecimento> findAllEstabelecimentos() {
        List<EstabelecimentoEntity> estabelecimentos = estabelecimentoRepository.findAll();
        return estabelecimentos.stream().map(entity -> modelMapper.map(entity, Estabelecimento.class)).collect(Collectors.toList());
    }

    public Estabelecimento findEstabelecimentoById(Long id) {
        if (id != null) {
            EstabelecimentoEntity estabelecimento = estabelecimentoRepository.findById(id).orElse(new EstabelecimentoEntity());
            return modelMapper.map(estabelecimento, Estabelecimento.class);
        }
        return null;
    }

    public Estabelecimento findEstabelecimentoByNome(String nome) {
        if (!nome.isEmpty()) {
            EstabelecimentoEntity estabelecimento = estabelecimentoRepository.findByNome(nome).orElse(new EstabelecimentoEntity());
            return modelMapper.map(estabelecimento, Estabelecimento.class);
        }
        return null;
    }

    public Estabelecimento findEstabelecimentoByNomeIdNot(String nome, Long id) {
        if (!nome.isEmpty() && id != null) {
            EstabelecimentoEntity estabelecimento = estabelecimentoRepository.findByNomeAndIdNot(nome, id).orElse(new EstabelecimentoEntity());
            return modelMapper.map(estabelecimento, Estabelecimento.class);
        }
        return null;
    }

    public List<Estabelecimento> findEstabelecimentoByNomeIgnoreCase(String nome) {
        List<EstabelecimentoEntity> estabelecimentos = estabelecimentoRepository.findByNomeContainingIgnoreCase(nome);
        return estabelecimentos.stream().map(entity -> modelMapper.map(entity, Estabelecimento.class)).collect(Collectors.toList());
    }

    public Estabelecimento salvarEstabelecimento(Estabelecimento estabelecimento) {
        EstabelecimentoEntity estabelecimentoEntity = modelMapper.map(estabelecimento, EstabelecimentoEntity.class);
        EstabelecimentoEntity salvarEstabelecimento = estabelecimentoRepository.save(estabelecimentoEntity);
        return modelMapper.map(salvarEstabelecimento, Estabelecimento.class);
    }

    public void excluirEstabelecimentoById(Long id) {
        if (id != null) {
            estabelecimentoRepository.deleteById(id);
        }
    }

    public boolean emailExistente(String email) {
        if (!email.isEmpty()) {
            Optional<EstabelecimentoEntity> estabelecimento = estabelecimentoRepository.findByEmail(email);
            return !estabelecimento.isEmpty();
        }
        return false;
    }

}
