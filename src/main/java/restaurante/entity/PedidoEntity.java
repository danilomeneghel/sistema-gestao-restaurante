package restaurante.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "pedido_item",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_cardapio_item")
    )
    private List<CardapioItemEntity> cardapioItens;

    private String observacao;

    @NotNull(message = "O total do pedido não pode estar em branco.")
    @DecimalMin(value = "0.01", message = "O total não pode ser R$0.00 ou negativo.")
    @DecimalMax(value = "99999999.99", message = "O total não pode ser maior que R$10000000.00")
    private BigDecimal total;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    private boolean status;

}
