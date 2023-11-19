package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.CardapioItemEntity;
import restaurante.entity.CategoriaEntity;
import restaurante.mapper.CardapioItemMapper;
import restaurante.model.CardapioItem;
import restaurante.model.Categoria;
import restaurante.repository.CardapioItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardapioItemService implements CardapioItemMapper {

    @Autowired
    private CardapioItemRepository cardapioItemRepository;

    @Autowired
    private ImagemService imagemService;

    private ModelMapper modelMapper = new ModelMapper();

    public List<CardapioItem> findAllCardapioItems() {
        List<CardapioItemEntity> cardapioItems = cardapioItemRepository.findAll();
        return cardapioItems.stream().map(entity -> modelMapper.map(entity, CardapioItem.class)).collect(Collectors.toList());
    }

    public CardapioItem findCardapioItemById(Long id) {
        Optional<CardapioItemEntity> cardapioItemEntity = cardapioItemRepository.findById(id);
        if (!cardapioItemEntity.isEmpty()) {
            return CardapioItemMapper.setCardapioItem(cardapioItemEntity.get());
        }
        return null;
    }

    public List<CardapioItem> findCardapioItemByCategoria(Categoria categoria) {
        CategoriaEntity categoriaEntity = modelMapper.map(categoria, CategoriaEntity.class);
        List<CardapioItemEntity> cardapioItems = cardapioItemRepository.findByCategoria(categoriaEntity);
        if (!cardapioItems.isEmpty()) {
            cardapioItems.stream().map(entity -> modelMapper.map(entity, CardapioItem.class)).collect(Collectors.toList());
        }
        return null;
    }

    public CardapioItem salvarCardapioItem(CardapioItem cardapioItem) {
        CardapioItemEntity cardapioItemEntity = modelMapper.map(cardapioItem, CardapioItemEntity.class);
        String produtos = String.join(", ", cardapioItem.getProdutos());
        cardapioItemEntity.setProdutos(produtos);
        CardapioItemEntity salvarCardapioItem = cardapioItemRepository.save(cardapioItemEntity);
        if (salvarCardapioItem != null) {
            imagemService.armazenarImagem(salvarCardapioItem.getId(), cardapioItem.getFiles());
        }
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
