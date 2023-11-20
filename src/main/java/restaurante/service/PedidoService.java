package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.CardapioItemEntity;
import restaurante.entity.CategoriaEntity;
import restaurante.entity.PedidoEntity;
import restaurante.model.Categoria;
import restaurante.model.Pedido;
import restaurante.repository.PedidoRepository;

import java.util.ArrayList;
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
        List<PedidoEntity> pedidos = pedidoRepository.findAll();
        return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
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
                for(PedidoEntity pedidoEntity : pedidosConfirmados) {
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
        PedidoEntity imo = modelMapper.map(pedido, PedidoEntity.class);
        PedidoEntity salvarPedido = pedidoRepository.save(imo);
        return modelMapper.map(salvarPedido, Pedido.class);
    }

    public void excluirPedidoById(Long id) {
        pedidoRepository.deleteById(id);
    }

}
