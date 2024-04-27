package br.com.libdolf.cinemamanager.controllers.user.dtos;

public record TokenResponse(
        String accessToken,
        Long expiresIn
) {
}
