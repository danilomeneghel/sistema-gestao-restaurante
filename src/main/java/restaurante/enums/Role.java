package restaurante.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ADMIN("ROLE_ADMIN", "Administrador"),
    USER("ROLE_USER","Usuário");

    private String valor;
    private String nome;

}
