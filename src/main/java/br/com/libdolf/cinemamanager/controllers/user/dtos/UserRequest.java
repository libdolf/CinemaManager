package br.com.libdolf.cinemamanager.controllers.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        String email,
        String username,
        @NotBlank String password
) { }
