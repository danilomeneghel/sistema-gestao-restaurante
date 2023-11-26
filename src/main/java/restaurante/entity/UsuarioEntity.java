package restaurante.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active;

    @NotBlank(message = "O nome não pode estar em branco.")
    private String name;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    private String email;

    @NotBlank(message = "O usuário não pode estar em branco.")
    private String username;

    @NotBlank(message = "A senha não pode estar em branco.")
    private String password;

    private String roles;

}
