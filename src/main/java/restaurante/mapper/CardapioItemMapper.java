package restaurante.mapper;

import org.springframework.stereotype.Component;
import restaurante.entity.CardapioItemEntity;
import restaurante.model.Cardapio;
import restaurante.model.CardapioItem;
import restaurante.model.Categoria;
import restaurante.model.Imagem;

import java.util.List;

@Component
public interface CardapioItemMapper {

    static CardapioItem setCardapioItem(CardapioItemEntity cardapioItemEntity, Categoria categoria, Cardapio cardapio,
                                        List<Imagem> imagens) {
        CardapioItem cardapioItem = new CardapioItem();
        cardapioItem.setId(cardapioItemEntity.getId());
        cardapioItem.setCategoria(categoria);
        cardapioItem.setCardapio(cardapio);
        cardapioItem.setNome(cardapioItemEntity.getNome());
        cardapioItem.setProdutos(cardapioItemEntity.getProdutos());
        cardapioItem.setPreco(cardapioItemEntity.getPreco());
        cardapioItem.setImagens(imagens);
        return cardapioItem;
    }

}
