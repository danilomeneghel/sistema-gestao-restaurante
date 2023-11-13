package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import restaurante.entity.ImagemEntity;
import restaurante.entity.PedidoEntity;
import restaurante.exception.FileStorageException;
import restaurante.model.Imagem;
import restaurante.model.Pedido;
import restaurante.repository.ImagemRepository;
import restaurante.repository.PedidoRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagemService {

    @Value("${file.dir-image}")
    public String DIR_IMAGE;

    @Value("${file.extensions-image}")
    private String EXTENSIONS_IMAGE;

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public String armazenarImagem(Long idImovel, MultipartFile[] arquivos) {
        String mensagem = null;
        if (!arquivos[0].isEmpty()) {
            try {
                Files.createDirectories(Paths.get(DIR_IMAGE));
            } catch (Exception ex) {
                throw new FileStorageException("Não foi possível criar o diretório para armazenar os arquivos.", ex);
            }
            for (MultipartFile arquivo : arquivos) {
                String nomeArquivo = UUID.randomUUID().toString().replace("-", "");
                if (nomeArquivo.contains("..")) {
                    throw new FileStorageException("Nome do arquivo contém sequência de caminho inválido " + nomeArquivo);
                }
                String extensaoArquivo = StringUtils.getFilenameExtension(arquivo.getOriginalFilename());
                Path novoNomeArquivo = Paths.get(nomeArquivo + "." + extensaoArquivo);
                if (EXTENSIONS_IMAGE.contains(extensaoArquivo)) {
                    this.copiarArquivo(DIR_IMAGE, novoNomeArquivo, arquivo);
                    Imagem imagem = new Imagem();
                    imagem.setFile(novoNomeArquivo.toString());
                    imagem.setPath(DIR_IMAGE);
                    Optional<PedidoEntity> imovelEntity = pedidoRepository.findById(idImovel);
                    Pedido pedido = modelMapper.map(imovelEntity.get(), Pedido.class);
                    imagem.setPedido(pedido);
                    Imagem imagemSalva = this.salvarImagem(imagem);
                    if (imagemSalva == null) {
                        throw new FileStorageException("Erro ao salvar imagem " + nomeArquivo + ". Por favor, tente novamente.");
                    } else {
                        mensagem = "Imagem(ns) salva(s) com sucesso";
                    }
                } else {
                    throw new FileStorageException("Arquivo com extensão inválida. Por favor, tente novamente.");
                }
            }
        } else {
            mensagem = "Nenhum arquivo enviado. Por favor, tente novamente.";
        }
        return mensagem;
    }

    public void copiarArquivo(String dir, Path novoNomeArquivo, MultipartFile arquivo) {
        try {
            Path targetLocation = Paths.get(dir).resolve(novoNomeArquivo);
            Files.copy(arquivo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Não foi possivel armazenar o arquivo " +
                    arquivo.getOriginalFilename() + ". Por favor, tente novamente.", ex);
        }
    }

    public Resource buscarArquivo(String nomeArquivo) {
        try {
            Path caminhoArquivo = Paths.get(DIR_IMAGE).resolve(nomeArquivo).normalize();
            Resource resource = new UrlResource(caminhoArquivo.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Arquivo não encontrado " + nomeArquivo);
            }
        } catch (IOException ex) {
            throw new FileStorageException("Não foi possivel localizar o arquivo " + nomeArquivo +
                    ". Por favor, tente novamente.");
        }
    }

    public Imagem salvarImagem(Imagem imagem) {
        ImagemEntity imagemEntity = modelMapper.map(imagem, ImagemEntity.class);
        ImagemEntity salvarImagem = imagemRepository.save(imagemEntity);
        return modelMapper.map(salvarImagem, Imagem.class);
    }

    public void excluirImagem(Long id) {
        imagemRepository.deleteById(id);
    }

}
