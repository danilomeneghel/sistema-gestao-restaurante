package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.*;
import restaurante.model.*;
import restaurante.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

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
            List<PedidoEntity> pedidos = pedidoRepository.findByStatusTrueAndCategoria(categoriaEntity);
            if (!pedidos.isEmpty()) {
                return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
            }
        }
        return null;
    }

    public List<Pedido> findPedidosByCardapio(Cardapio cardapio) {
        if (cardapio.getId() != null) {
            CardapioEntity cardapioEntity = modelMapper.map(cardapio, CardapioEntity.class);
            List<PedidoEntity> pedidos = pedidoRepository.findByCardapio(cardapioEntity);
            if (!pedidos.isEmpty()) {
                return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
            }
        }
        return null;
    }

    public List<Pedido> findPedidosByProduto(Produto produto) {
        if (produto.getId() != null) {
            ProdutoEntity produtoEntity = modelMapper.map(produto, ProdutoEntity.class);
            List<PedidoEntity> pedidos = pedidoRepository.findByProduto(produtoEntity);
            if (!pedidos.isEmpty()) {
                return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
            }
        }
        return null;
    }

    public List<Pedido> findPedidosByPrato(CardapioItem cardapioItem) {
        if (cardapioItem.getId() != null) {
            CardapioItemEntity cardapioItemEntity = modelMapper.map(cardapioItem, CardapioItemEntity.class);
            List<PedidoEntity> pedidos = pedidoRepository.findByPrato(cardapioItemEntity);
            if (!pedidos.isEmpty()) {
                return pedidos.stream().map(entity -> modelMapper.map(entity, Pedido.class)).collect(Collectors.toList());
            }
        }
        return null;
    }

    public List<Pedido> findPedidosByBairro(Bairro bairro) {
        if (bairro.getId() != null) {
            BairroEntity bairroEntity = modelMapper.map(bairro, BairroEntity.class);
            List<PedidoEntity> pedidos = pedidoRepository.findByBairro(bairroEntity);
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
