package br.com.libdolf.cinemamanager.controllers.user.dtos;

import java.util.List;

public record UserResponse(
        String userId,
        String email,
        String username,
        List<String> rolesNames
) {
}
