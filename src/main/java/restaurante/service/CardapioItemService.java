package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.CardapioItemEntity;
import restaurante.model.CardapioItem;
import restaurante.repository.CardapioItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardapioItemService {

    @Autowired
    private CardapioItemRepository cardapioItemRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<CardapioItem> findAllCardapioItems() {
        List<CardapioItemEntity> cardapioItems = cardapioItemRepository.findAll();
        return cardapioItems.stream().map(entity -> modelMapper.map(entity, CardapioItem.class)).collect(Collectors.toList());
    }

    public CardapioItem findCardapioItemById(Long id) {
        Optional<CardapioItemEntity> cardapioItemEntity = cardapioItemRepository.findById(id);
        if (!cardapioItemEntity.isEmpty()) {
            return modelMapper.map(cardapioItemEntity.get(), CardapioItem.class);
        }
        return null;
    }

    public CardapioItem salvarCardapioItem(CardapioItem cardapioItem) {
        CardapioItemEntity cardapioItemEntity = modelMapper.map(cardapioItem, CardapioItemEntity.class);
        CardapioItemEntity salvarCardapioItem = cardapioItemRepository.save(cardapioItemEntity);
        return modelMapper.map(salvarCardapioItem, CardapioItem.class);
    }

    public void excluirCardapioItem(Long id) {
        cardapioItemRepository.deleteById(id);
    }

    public List<CardapioItem> findCardapioItemByNome(String nome) {
        List<CardapioItemEntity> cardapioItems = cardapioItemRepository.findByNomeContainingIgnoreCase(nome);
        return cardapioItems.stream().map(entity -> modelMapper.map(entity, CardapioItem.class)).collect(Collectors.toList());
    }

}
