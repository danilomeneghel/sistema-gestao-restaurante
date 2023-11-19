package restaurante.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private Long id;

    private CardapioItem cardapioItem;

    private String observacao;

    private BigDecimal total;

    private LocalDateTime data;

    private boolean status;

}
