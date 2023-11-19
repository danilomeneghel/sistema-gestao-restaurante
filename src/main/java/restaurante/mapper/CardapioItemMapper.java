package restaurante.mapper;

import org.springframework.stereotype.Component;
import restaurante.entity.CardapioItemEntity;
import restaurante.model.CardapioItem;

@Component
public interface CardapioItemMapper {

    static CardapioItem setCardapioItem(CardapioItemEntity cardapioItemEntity) {
        CardapioItem cardapioItem = new CardapioItem();
        cardapioItem.setId(cardapioItemEntity.getId());
        //cardapioItem.setCategoria(cardapioItemEntity.getCategoria());
        //cardapioItem.setCardapio(cardapioItemEntity.getCardapio());
        cardapioItem.setNome(cardapioItemEntity.getNome());
        String[] produtos = cardapioItemEntity.getProdutos().split(", ");
        cardapioItem.setProdutos(produtos);
        cardapioItem.setPreco(cardapioItemEntity.getPreco());
        //cardapioItem.setImagens(cardapioItemEntity.getImagens());
        return cardapioItem;
    }

}
