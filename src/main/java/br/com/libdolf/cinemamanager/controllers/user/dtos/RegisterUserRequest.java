package br.com.libdolf.cinemamanager.controllers.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
        @NotBlank String email,
        @NotBlank String username,
        @NotBlank String password
) { }
