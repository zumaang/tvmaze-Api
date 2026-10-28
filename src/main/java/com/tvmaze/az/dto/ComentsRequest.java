package com.tvmaze.az.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentsRequest(

    @NotBlank(message = "El comentario no puede estar vacio")
    String comment,

    @NotNull(message = "La calificacion es obligatoria")
    @Min(value = 0, message = "La calificacion minima es 0")
    @Max(value = 5, message = "La calificacion maxima es 5")
    Integer rating
    ) {
}
