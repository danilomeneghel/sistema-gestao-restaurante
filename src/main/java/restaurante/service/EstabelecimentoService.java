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
    private EstabelecimentoRepository rep;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Estabelecimento> findAllEstabelecimentos() {
        List<EstabelecimentoEntity> estabelecimentos = rep.findAll();
        return estabelecimentos.stream().map(entity -> modelMapper.map(entity, Estabelecimento.class)).collect(Collectors.toList());
    }

    public Estabelecimento findEstabelecimentoById(Long id) {
        Optional<EstabelecimentoEntity> estabelecimento = rep.findById(id);
        return modelMapper.map(estabelecimento.get(), Estabelecimento.class);
    }

    public Estabelecimento findEstabelecimentoByNome(String nome) {
        Optional<EstabelecimentoEntity> estabelecimento = rep.findByNome(nome);
        return modelMapper.map(estabelecimento.get(), Estabelecimento.class);
    }

    public Estabelecimento salvarEstabelecimento(Estabelecimento estabelecimento) {
        EstabelecimentoEntity estabelecimentoEntity = modelMapper.map(estabelecimento, EstabelecimentoEntity.class);
        EstabelecimentoEntity salvarEstabelecimento = rep.save(estabelecimentoEntity);
        return modelMapper.map(salvarEstabelecimento, Estabelecimento.class);
    }

    public void excluirEstabelecimentoById(Long id) {
        rep.deleteById(id);
    }

    public boolean emailExistente(String email) {
        Optional<EstabelecimentoEntity> estabelecimento = rep.findByEmail(email);
        return !estabelecimento.isEmpty();
    }

}
