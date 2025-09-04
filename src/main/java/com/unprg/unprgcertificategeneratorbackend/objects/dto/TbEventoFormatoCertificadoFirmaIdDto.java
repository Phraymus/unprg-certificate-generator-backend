package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificadoFirmaId}
 */
@Data
@Builder
@Value
public class TbEventoFormatoCertificadoFirmaIdDto implements Serializable {
    Integer idtbFirma;
    Integer tbEventoFormatoCertificadoIdtbEvento;
}