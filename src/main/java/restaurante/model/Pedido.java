package restaurante.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private Long id;

    private List<CardapioItem> cardapioItens;

    private String[] cardapioItensArray;

    private String observacao;

    private BigDecimal total;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    private boolean status;

}
