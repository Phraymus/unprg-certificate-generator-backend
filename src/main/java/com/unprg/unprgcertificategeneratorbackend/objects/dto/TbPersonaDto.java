package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbPersona}
 */
@Data
@Builder
@Value
public class TbPersonaDto implements Serializable {
    Integer id;
    String nombres;
    String apellidoPaterno;
    String apellidoMaterno;
    String dni;
    String telefono;
    String email;
}