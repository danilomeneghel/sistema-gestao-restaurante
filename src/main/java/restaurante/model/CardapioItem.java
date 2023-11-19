package restaurante.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardapioItem {

    private Long id;

    private Categoria categoria;

    private Cardapio cardapio;

    private String nome;

    private String[] produtos;

    private BigDecimal preco;

    private List<Imagem> imagens;

    private MultipartFile[] files;

}
