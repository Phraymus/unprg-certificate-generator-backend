package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbUsuario}
 */
@Data
@Builder
@Value
public class TbUsuarioDto implements Serializable {
    Integer id;
    String usuario;
    String clave;
    TbPersonaDto idtbPersona;
}