package br.com.libdolf.cinemamanager.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("Role not found");
    }
}
