package br.com.libdolf.cinemamanager.services.user;

import br.com.libdolf.cinemamanager.controllers.user.dtos.*;
import br.com.libdolf.cinemamanager.entities.user.Role;
import br.com.libdolf.cinemamanager.entities.user.User;
import br.com.libdolf.cinemamanager.exceptions.RoleNotFoundException;
import br.com.libdolf.cinemamanager.exceptions.UserAlreadyExistsException;
import br.com.libdolf.cinemamanager.exceptions.UserNotFoundException;
import br.com.libdolf.cinemamanager.mapper.UserMapper;
import br.com.libdolf.cinemamanager.repositories.user.RoleRepository;
import br.com.libdolf.cinemamanager.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Value("${token.expiresIn}")
    private Long expiresIn;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final UserMapper mapper;

    public UserService(UserRepository repository,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtEncoder jwtEncoder,
                       UserMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.mapper = mapper;
    }

    public TokenResponse register(RegisterUserRequest request) {
        var existingUser = userRepository.findByUsername(request.username());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        var role = roleRepository.findByName(Role.Values.USER.name()).orElseThrow(RoleNotFoundException::new);
        var user = mapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(role));

        userRepository.save(user);
        return login(new UserRequest(request.email(), request.username(), request.password()));
    }

    public TokenResponse login(UserRequest request) {
        Optional<User> user;

        if(request.email() == null){
            user = userRepository.findByUsername(request.username());
        } else user = userRepository.findByEmail(request.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(request, passwordEncoder)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        var now = Instant.now();
        var subject = user.get().getUserId().toString();
        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        return new TokenResponse(jwtValues(subject, now, this.expiresIn, scopes), this.expiresIn);
    }

    public void update(UUID id, UpdateUserRequest request) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Set<Role> roles = new HashSet<>();
        for (var roleName : request.rolesNames()) {
            roleRepository.findByName(roleName).ifPresent(roles::add);
        }
        user.update(request, roles);
        userRepository.save(user);
    }

    public Page<UserResponse> findAllUsers(Pageable pageable) {
        var users = userRepository.findAll(pageable);
        return users.map(mapper::toResponse);
    }

    private String jwtValues(String subject, Instant instant, Long expiresIn, String scopes){
        var claims = JwtClaimsSet.builder()
                .issuer("CinemaManager")
                .subject(subject)
                .expiresAt(instant.plusSeconds(expiresIn))
                .issuedAt(instant)
                .claim("scope",scopes)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
