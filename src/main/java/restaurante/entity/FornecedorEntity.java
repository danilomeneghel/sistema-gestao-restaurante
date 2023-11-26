package restaurante.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "fornecedor")
@Data
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean ativo;

    @NotBlank(message = "O nome não pode estar em branco.")
    private String nome;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    private String email;

    @NotBlank(message = "O telefone não pode estar em branco.")
    private String telefone;

    @NotBlank(message = "A rua não pode estar em branco.")
    private String rua;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bairro")
    private BairroEntity bairro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio")
    private MunicipioEntity municipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private EstadoEntity estado;

}
