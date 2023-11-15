package restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Imagem {

    private Long id;

    private String file;

    private String path;

    @JsonIgnore
    private CardapioItem cardapioItem;

}
