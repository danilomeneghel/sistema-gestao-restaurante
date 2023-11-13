package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.CardapioEntity;
import restaurante.model.Cardapio;
import restaurante.repository.CardapioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    @Autowired
    private CardapioRepository cardapioRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Cardapio> findAllCardapios() {
        List<CardapioEntity> cardapios = cardapioRepository.findAll();
        return cardapios.stream().map(entity -> modelMapper.map(entity, Cardapio.class)).collect(Collectors.toList());
    }

    public Cardapio findCardapioById(Long id) {
        Optional<CardapioEntity> cardapioEntity = cardapioRepository.findById(id);
        if (!cardapioEntity.isEmpty()) {
            return modelMapper.map(cardapioEntity.get(), Cardapio.class);
        }
        return null;
    }

    public Cardapio salvarCardapio(Cardapio cardapio) {
        CardapioEntity cardapioEntity = modelMapper.map(cardapio, CardapioEntity.class);
        CardapioEntity salvarCardapio = cardapioRepository.save(cardapioEntity);
        return modelMapper.map(salvarCardapio, Cardapio.class);
    }

    public void excluirCardapio(Long id) {
        cardapioRepository.deleteById(id);
    }

    public List<Cardapio> findCardapioByNome(String nome) {
        List<CardapioEntity> cardapios = cardapioRepository.findByNomeContainingIgnoreCase(nome);
        return cardapios.stream().map(entity -> modelMapper.map(entity, Cardapio.class)).collect(Collectors.toList());
    }

}
