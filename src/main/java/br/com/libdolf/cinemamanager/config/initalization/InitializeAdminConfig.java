package br.com.libdolf.cinemamanager.config.initalization;

import br.com.libdolf.cinemamanager.entities.user.User;
import br.com.libdolf.cinemamanager.repositories.user.RoleRepository;
import br.com.libdolf.cinemamanager.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;


@Configuration
public class InitializeAdminConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${auth.admin.email}")
    private String email;
    @Value("${auth.admin.username}")
    private String username;
    @Value("${auth.admin.password}")
    private String password;

    public InitializeAdminConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = new HashSet<>(roleRepository.findAll());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                (user)->{
                    System.out.println("Admin already exists");
                },
                ()->{
                    var user = new User(
                            null,
                            email,
                            username,
                            passwordEncoder.encode(password),
                            roleAdmin);
                    userRepository.save(user);
                    System.out.println("Admin created");
                }
        );


    }
}
