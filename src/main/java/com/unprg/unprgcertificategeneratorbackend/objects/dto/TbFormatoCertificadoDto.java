package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificado}
 */
@Data
@Builder
@Value
public class TbFormatoCertificadoDto implements Serializable {
    Integer id;
    String codigo;
    String nombreFormato;
    String rutaFormato;
    TbUsuarioDto idtbUsuario;
}