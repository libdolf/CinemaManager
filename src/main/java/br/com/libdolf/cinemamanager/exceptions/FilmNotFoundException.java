package br.com.libdolf.cinemamanager.exceptions;

public class FilmNotFoundException extends RuntimeException{
    public FilmNotFoundException(){
        super("Film not found, please try again");
    }
}
