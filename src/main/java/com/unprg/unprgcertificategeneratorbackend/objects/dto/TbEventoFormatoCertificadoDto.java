package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificado}
 */
@Data
@Builder
@Value
public class TbEventoFormatoCertificadoDto implements Serializable {
    Integer id;
    TbEventoDto tbEvento;
    TbFormatoCertificadoDto idtbFormatoCertificado;
    String codigo;
    String descripcion;
    LocalDate fecha;
}