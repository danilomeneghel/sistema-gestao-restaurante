package restaurante.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import restaurante.entity.UsuarioEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(UsuarioEntity usuarioEntity) {
        this.name = usuarioEntity.getName();
        this.username = usuarioEntity.getUsername();
        this.email = usuarioEntity.getEmail();
        this.password = usuarioEntity.getPassword();
        this.active = usuarioEntity.isActive();
        this.authorities = Arrays.stream(usuarioEntity.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

}
