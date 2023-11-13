package restaurante.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.entity.CardapioItemEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private Long id;

    private List<CardapioItemEntity> cardapioItems;

    private String observacao;

    private BigDecimal total;

    private LocalDateTime data;

    private boolean status;

}
