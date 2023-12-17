package restaurante.service;

import restaurante.entity.EstabelecimentoEntity;
import restaurante.model.Estabelecimento;
import restaurante.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<EstabelecimentoEntity> estabelecimento = estabelecimentoRepository.findById(id);
        if (!estabelecimento.isEmpty()) {
            return modelMapper.map(estabelecimento.get(), Estabelecimento.class);
        }
        return null;
    }

    public Estabelecimento findEstabelecimentoByNome(String nome) {
        Optional<EstabelecimentoEntity> estabelecimento = estabelecimentoRepository.findByNome(nome);
        if (!estabelecimento.isEmpty()) {
            return modelMapper.map(estabelecimento.get(), Estabelecimento.class);
        }
        return null;
    }

    public Estabelecimento findEstabelecimentoByNomeIdNot(String nome, Long id) {
        Optional<EstabelecimentoEntity> estabelecimento = estabelecimentoRepository.findByNomeAndIdNot(nome, id);
        if (!estabelecimento.isEmpty()) {
            return modelMapper.map(estabelecimento.get(), Estabelecimento.class);
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
        estabelecimentoRepository.deleteById(id);
    }

    public boolean emailExistente(String email) {
        Optional<EstabelecimentoEntity> estabelecimento = estabelecimentoRepository.findByEmail(email);
        return !estabelecimento.isEmpty();
    }

}
