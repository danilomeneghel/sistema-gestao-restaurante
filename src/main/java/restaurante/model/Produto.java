package restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private Long id;

    private String nome;

    @JsonIgnore
    private CardapioItem cardapioItem;

    private List<Imagem> imagens;

    private MultipartFile[] files;

}
