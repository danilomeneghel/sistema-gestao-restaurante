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
            CategoriaEntity categoriaEntity = modelMapper.map(categoria, CategoriaEntity.class);
            List<PedidoEntity> pedidos = pedidoRepository.findByStatusTrueAndCardapioItemCategoria(categoriaEntity);
            if (!pedidos.isEmpty()) {
                return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
            }
        }
        return null;
    }

    public List<Pedido> findPedidosByCardapioItem(CardapioItem cardapioItem) {
        if (cardapioItem.getId() != null) {
            CardapioItemEntity cardapioItemEntity = modelMapper.map(cardapioItem, CardapioItemEntity.class);
            List<PedidoEntity> pedidos = pedidoRepository.findByCardapioItemNome(cardapioItemEntity.getNome());
            if (!pedidos.isEmpty()) {
                return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
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
