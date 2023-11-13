package restaurante.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cardapio_item")
@Data
public class CardapioItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cardapio")
    private CardapioEntity cardapio;

    @NotBlank(message = "O nome do item do cardápio não pode estar em branco.")
    private String nome;

    @OneToMany(mappedBy = "cardapio_item", cascade = CascadeType.REMOVE)
    private List<CategoriaEntity> categorias;

    @OneToMany(mappedBy = "cardapio_item", cascade = CascadeType.REMOVE)
    private List<ProdutoEntity> produtos;

    @NotNull(message = "O preço do item do cardápio não pode estar em branco.")
    @DecimalMin(value = "0.01", message = "O preço não pode ser R$0.00 ou negativo.")
    @DecimalMax(value = "99999999.99", message = "O preço não pode ser maior que R$10000000.00")
    private BigDecimal preco;

}
