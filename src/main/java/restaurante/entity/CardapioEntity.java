package restaurante.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "cardapio")
@Data
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cardápio não pode estar em branco.")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estabelecimento")
    private EstabelecimentoEntity estabelecimento;

}
