package restaurante.service;

import restaurante.entity.UsuarioEntity;
import restaurante.model.Usuario;
import restaurante.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository rep;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Usuario> findAllUsuarios() {
        List<UsuarioEntity> usuarios = rep.findAll();
        usuarios.forEach(usuario -> usuario.setPassword(null));
        return usuarios.stream().map(entity -> modelMapper.map(entity, Usuario.class)).collect(Collectors.toList());
    }

    public Usuario findUsuarioById(Long id) {
        Optional<UsuarioEntity> usuario = rep.findById(id);
        if(!usuario.isEmpty()) {
            usuario.get().setPassword(null);
            return modelMapper.map(usuario.get(), Usuario.class);
        }
        return null;
    }

    public Usuario findUsuarioByUsername(String username) {
        Optional<UsuarioEntity> usuario = rep.findByUsername(username);
        if(!usuario.isEmpty()) {
            usuario.get().setPassword(null);
            return modelMapper.map(usuario.get(), Usuario.class);
        }
        return null;
    }

    public Usuario findUsuarioByUsernameIdNot(String username, Long id) {
        Optional<UsuarioEntity> usuario = rep.findByUsernameAndIdNot(username, id);
        if(!usuario.isEmpty()) {
            usuario.get().setPassword(null);
            return modelMapper.map(usuario.get(), Usuario.class);
        }
        return null;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        UsuarioEntity usuarioEntity = modelMapper.map(usuario, UsuarioEntity.class);
        UsuarioEntity salvarUsuario = rep.save(usuarioEntity);
        return modelMapper.map(salvarUsuario, Usuario.class);
    }

    public void excluirUsuarioById(Long id) {
        rep.deleteById(id);
    }

    public boolean emailExistente(String email) {
        Optional<UsuarioEntity> usuario = rep.findByEmail(email);
        return !usuario.isEmpty();
    }

}
