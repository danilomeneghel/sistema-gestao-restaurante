package restaurante.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.entity.BairroEntity;
import restaurante.entity.EstadoEntity;
import restaurante.entity.MunicipioEntity;
import restaurante.model.Bairro;
import restaurante.model.Estado;
import restaurante.model.Municipio;
import restaurante.repository.BairroRepository;
import restaurante.repository.EstadoRepository;
import restaurante.repository.MunicipioRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalidadeService {


    @Autowired
    private EstadoRepository estRep;

    @Autowired
    private MunicipioRepository munRep;

    @Autowired
    private BairroRepository baiRep;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Estado> findAllEstados() {
        List<EstadoEntity> estados = estRep.findAll();
        return estados.stream().map(entity -> modelMapper.map(entity, Estado.class)).collect(Collectors.toList());
    }

    public Estado findEstadoById(Long id) {
        if (id != null) {
            EstadoEntity estado = estRep.findById(id).orElse(new EstadoEntity());
            return modelMapper.map(estado, Estado.class);
        }
        return null;
    }

    public List<Estado> findEstadoByNome(String nome) {
        List<EstadoEntity> estados = estRep.findByNomeContainingIgnoreCase(nome);
        return estados.stream().map(entity -> modelMapper.map(entity, Estado.class)).collect(Collectors.toList());
    }

    public Estado findEstadoByMunicipio(Municipio municipio) {
        Estado estadoEscolhido = null;
        List<Estado> estados = findAllEstados();
        for (Estado estado : estados) {
            if (estado.getId() == municipio.getEstado().getId()) {
                estadoEscolhido = estado;
            }
        }
        return estadoEscolhido;
    }

    public Estado salvarEstado(Estado estado) {
        EstadoEntity estadoEntity = modelMapper.map(estado, EstadoEntity.class);
        EstadoEntity salvarEstado = estRep.save(estadoEntity);
        return modelMapper.map(salvarEstado, Estado.class);
    }

    public void excluirEstadoById(Long id) {
        if (id != null) {
            estRep.deleteById(id);
        }
    }

    public List<Municipio> findAllMunicipios() {
        List<MunicipioEntity> municipios = munRep.findAll();
        return municipios.stream().map(entity -> modelMapper.map(entity, Municipio.class)).collect(Collectors.toList());
    }

    public Municipio findMunicipioById(Long id) {
        if (id != null) {
            MunicipioEntity municipio = munRep.findById(id).orElse(new MunicipioEntity());
            return modelMapper.map(municipio, Municipio.class);
        }
        return null;
    }

    public List<Municipio> findMunicipioByNome(String nome) {
        List<MunicipioEntity> municipios = munRep.findByNomeContainingIgnoreCase(nome);
        return municipios.stream().map(entity -> modelMapper.map(entity, Municipio.class)).collect(Collectors.toList());
    }

    public Municipio findMunicipioByBairro(Bairro bairro) {
        Municipio municipioEscolhido = null;
        List<Municipio> municipios = findAllMunicipios();
        for (Municipio municipio : municipios) {
            if (municipio.getId() == bairro.getMunicipio().getId()) {
                municipioEscolhido = municipio;
            }
        }
        return municipioEscolhido;
    }

    public Municipio salvarMunicipio(Municipio municipio) {
        MunicipioEntity municipioEntity = modelMapper.map(municipio, MunicipioEntity.class);
        MunicipioEntity salvarMunicipio = munRep.save(municipioEntity);
        return modelMapper.map(salvarMunicipio, Municipio.class);
    }

    public void excluirMunicipioById(Long id) {
        if (id != null) {
            munRep.deleteById(id);
        }
    }

    public List<Bairro> findAllBairros() {
        List<BairroEntity> bairros = baiRep.findAll();
        return bairros.stream().map(entity -> modelMapper.map(entity, Bairro.class)).collect(Collectors.toList());
    }

    public Bairro findBairroById(Long id) {
        if (id != null) {
            BairroEntity bairro = baiRep.findById(id).orElse(new BairroEntity());
            return modelMapper.map(bairro, Bairro.class);
        }
        return null;
    }

    public List<Bairro> findBairroByNome(String nome) {
        List<BairroEntity> bairros = baiRep.findByNomeContainingIgnoreCase(nome);
        return bairros.stream().map(entity -> modelMapper.map(entity, Bairro.class)).collect(Collectors.toList());
    }

    public Bairro salvarBairro(Bairro bairro) {
        BairroEntity bairroEntity = modelMapper.map(bairro, BairroEntity.class);
        BairroEntity salvarBairro = baiRep.save(bairroEntity);
        return modelMapper.map(salvarBairro, Bairro.class);
    }

    public void excluirBairroById(Long id) {
        if (id != null) {
            baiRep.deleteById(id);
        }
    }

    public List<Municipio> findMunicipioPerEstado(Long idEstado) {
        Optional<EstadoEntity> estado = estRep.findById(idEstado);
        List<MunicipioEntity> municipios = munRep.findByEstado(estado.get());
        return municipios.stream().map(entity -> modelMapper.map(entity, Municipio.class)).collect(Collectors.toList());
    }

    public Map<Long, String> findMunicipioAsMapPerEstado(Long idEstado) {
        Map<Long, String> response = new HashMap<Long, String>();
        List<Municipio> municipios = findMunicipioPerEstado(idEstado);
        for (Municipio municipio : municipios) {
            response.put(municipio.getId(), municipio.getNome());
        }
        return response;
    }

    public List<Bairro> findBairroPerMunicipio(Long idMunicipio) {
        MunicipioEntity municipio = munRep.findById(idMunicipio).get();
        List<BairroEntity> bairros = baiRep.findByMunicipio(municipio);
        return bairros.stream().map(entity -> modelMapper.map(entity, Bairro.class)).collect(Collectors.toList());
    }

    public Map<Long, String> findBairroAsMapPerMunicipio(Long idMunicipio) {
        Map<Long, String> response = new HashMap<Long, String>();
        List<Bairro> bairros = findBairroPerMunicipio(idMunicipio);
        for (Bairro bairro : bairros) {
            response.put(bairro.getId(), bairro.getNome());
        }
        return response;
    }

}
