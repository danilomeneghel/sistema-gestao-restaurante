package restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import restaurante.entity.ImagemEntity;
import restaurante.entity.PedidoEntity;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardapioItem {

    private Long id;

    private Categoria categoria;

    @JsonIgnore
    private Cardapio cardapio;

    private String nome;

    private String produtos;

    private BigDecimal preco;

    private List<Imagem> imagens;

    private MultipartFile[] files;

    @JsonIgnore
    private List<Pedido> pedidos;

}
