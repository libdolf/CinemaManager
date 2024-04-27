package br.com.libdolf.cinemamanager.exceptions.handler;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionsDetails {
    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;
}
