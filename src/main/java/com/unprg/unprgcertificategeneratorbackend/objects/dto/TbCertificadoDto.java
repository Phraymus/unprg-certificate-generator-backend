package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbCertificado}
 */
@Data
@Builder
@Value
public class TbCertificadoDto implements Serializable {
    Integer id;
    String codigoQr;
    String urlCertificado;
    TbParticipanteDto tbParticipante;
}