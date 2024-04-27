package br.com.libdolf.cinemamanager.controllers.user.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateUserRequest(
        @NotBlank String email,
        @NotBlank String username,
        @NotBlank String password,
        @NotNull List<String> rolesNames
) {
}
