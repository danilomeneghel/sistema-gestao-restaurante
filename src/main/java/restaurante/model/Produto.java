package restaurante.model;

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

    private List<Imagem> imagens;

    private MultipartFile[] files;

}
