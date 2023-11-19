package restaurante.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private Long id;

    private String nome;

    @Override
    public String toString() {
        return "{\"id\":" + id + ", \"nome\":\"" + nome + "\"}";
    }

}
