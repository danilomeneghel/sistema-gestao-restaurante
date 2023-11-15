package restaurante.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categoria")
@Data
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria não pode estar em branco.")
    private String nome;

}
