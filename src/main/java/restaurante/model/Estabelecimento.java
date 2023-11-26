package restaurante.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estabelecimento {

    private Long id;

    private boolean ativo;

    private String nome;

    private String email;

    private String telefone;

    private String rua;

    private Bairro bairro;

    private Municipio municipio;

    private Estado estado;

}
