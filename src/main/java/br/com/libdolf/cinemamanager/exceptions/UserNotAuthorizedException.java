package br.com.libdolf.cinemamanager.exceptions;

public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException() {
        super("User not authorized");
    }
}
