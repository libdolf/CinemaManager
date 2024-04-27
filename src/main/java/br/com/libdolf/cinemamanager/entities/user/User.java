package br.com.libdolf.cinemamanager.entities.user;

import br.com.libdolf.cinemamanager.controllers.user.dtos.UpdateUserRequest;
import br.com.libdolf.cinemamanager.controllers.user.dtos.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public List<String> getRolesNames(){
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    public boolean isLoginCorrect(UserRequest request, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(request.password(), this.password);
    }

    public void update(UpdateUserRequest request, Set<Role> roles) {
        this.email = request.email();
        this.username = request.username();
        this.password = request.password();
        this.roles = roles;
    }
}