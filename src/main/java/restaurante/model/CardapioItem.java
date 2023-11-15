package restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.entity.PedidoEntity;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardapioItem {

    private Long id;

    @JsonIgnore
    private Cardapio cardapio;

    private String nome;

    private Categoria categoria;

    private List<Produto> produtos;

    private BigDecimal preco;

    @JsonIgnore
    private List<Pedido> pedidos;

}
