package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.CardapioItemEntity;
import restaurante.entity.CategoriaEntity;
import restaurante.entity.PedidoEntity;
import restaurante.model.CardapioItem;
import restaurante.model.Categoria;
import restaurante.model.Pedido;
import restaurante.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CardapioItemService cardapioItemService;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Pedido> findAllPedidos() {
        List<PedidoEntity> pedidosEntiity = pedidoRepository.findAll();
        List<Pedido> pedidos = pedidosEntiity.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
        for(Pedido pedido : pedidos) {
            StringBuilder cardapioItens = new StringBuilder();
            for (CardapioItem cardapioItem : pedido.getCardapioItens()) {
                cardapioItens.append(cardapioItem.getNome()).append(", ");
            }
            if (cardapioItens.length() > 0) {
                cardapioItens.delete(cardapioItens.length() - 2, cardapioItens.length());
            }
            pedido.setCardapioItensString(cardapioItens.toString());
        }
        return pedidos;
    }

    public List<Pedido> findPedidosByConfirmado() {
        List<PedidoEntity> pedidos = pedidoRepository.findByStatusTrue();
        return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
    }

    public Pedido findPedidoById(Long id) {
        Optional<PedidoEntity> pedidoEntity = pedidoRepository.findById(id);
        if (!pedidoEntity.isEmpty()) {
            return modelMapper.map(pedidoEntity.get(), Pedido.class);
        }
        return null;
    }

    public List<Pedido> findPedidosByConfirmadoAndCategoria(Categoria categoria) {
        if (categoria.getId() != null) {
            List<PedidoEntity> pedidosConfirmados = pedidoRepository.findByStatusTrue();
            List<PedidoEntity> pedidosCategoria = new ArrayList<>();
            if (!pedidosConfirmados.isEmpty()) {
                for (PedidoEntity pedidoEntity : pedidosConfirmados) {
                    for (CardapioItemEntity cardapioItemEntity : pedidoEntity.getCardapioItens()) {
                        if (cardapioItemEntity.getCategoria().getNome() == categoria.getNome()) {
                            pedidosCategoria.add(pedidoEntity);
                        }
                    }
                }
                return pedidosCategoria.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
            }
        }
        return null;
    }

    public Pedido salvarPedido(Pedido pedido) {
        List<CardapioItem> cardapioItemList = new ArrayList<>();
        String[] cardapioItensArray = pedido.getCardapioItensArray();
        int qtdeItensArray = cardapioItensArray.length;
        if (qtdeItensArray > 0) {
            for (int i = 0; i < qtdeItensArray; i++) {
                Long id = Long.valueOf(i + 1);
                String nome = cardapioItensArray[i];
                CardapioItem cardapioItem = new CardapioItem();
                cardapioItem.setId(id);
                cardapioItem.setNome(nome);
                cardapioItemList.add(cardapioItem);
            }
        }
        pedido.setCardapioItens(cardapioItemList);
        PedidoEntity pedidoEntity = modelMapper.map(pedido, PedidoEntity.class);
        PedidoEntity salvarPedido = pedidoRepository.save(pedidoEntity);
        return modelMapper.map(salvarPedido, Pedido.class);
    }

    public void excluirPedidoById(Long id) {
        pedidoRepository.deleteById(id);
    }

}
