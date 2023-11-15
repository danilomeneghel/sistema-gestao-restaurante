package restaurante.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cardapio_item")
    private CardapioItemEntity cardapioItem;

    private String observacao;

    @NotNull(message = "O total do pedido não pode estar em branco.")
    @DecimalMin(value = "0.01", message = "O total não pode ser R$0.00 ou negativo.")
    @DecimalMax(value = "99999999.99", message = "O total não pode ser maior que R$10000000.00")
    private BigDecimal total;

    private LocalDateTime data;

    private boolean status;

}
