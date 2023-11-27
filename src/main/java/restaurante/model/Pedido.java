package restaurante.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private Long id;

    private Estabelecimento estabelecimento;

    private List<CardapioItem> cardapioItens;

    private String[] cardapioItensArray;

    private String cardapioItensString;

    private String observacao;

    private BigDecimal total;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    private boolean status;

}
