package restaurante.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedido {

    CANCELADO(false, "Cancelado"),
    CONFIRMADO(true, "Confirmado");

    private boolean valor;
    private String nome;

}
