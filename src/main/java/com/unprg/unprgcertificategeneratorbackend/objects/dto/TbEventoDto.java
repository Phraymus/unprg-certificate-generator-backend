package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEvento}
 */
@Data
@Builder
@Value
public class TbEventoDto implements Serializable {
    Integer id;
    String codigo;
    String nombre;
    LocalDate fechaInicio;
    LocalDate fechaFin;
}