package restaurante.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Ativo {

    INATIVO(false, "Inativo"),
    ATIVO(true, "Ativo");

    private boolean valor;
    private String nome;

}
