package br.com.libdolf.cinemamanager.controllers.user;

import br.com.libdolf.cinemamanager.controllers.user.dtos.*;
import br.com.libdolf.cinemamanager.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user and receive a valid token",
            description = "Email, username and password is mandatory",
            tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error in request")
    })
    public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login",
            description = "Return a valid jwt token",
            tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error in request")
    })
    public ResponseEntity<TokenResponse> login(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Update a user",
            description = "Only Admin updates a user",
            tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error in request")
    })
    public ResponseEntity<Void> update(@RequestParam UUID id, @RequestBody @Valid UpdateUserRequest request) {
        service.update(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_MODERATOR')")
    @Operation(summary = "List all users",
            description = "Admin and Moderators can use",
            tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error in request")
    })
    public ResponseEntity<Page<UserResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAllUsers(pageable));
    }
}
