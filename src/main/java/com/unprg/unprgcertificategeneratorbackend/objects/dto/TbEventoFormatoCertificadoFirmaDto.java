package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificadoFirma}
 */
@Data
@Builder
@Value
public class TbEventoFormatoCertificadoFirmaDto implements Serializable {
    TbEventoFormatoCertificadoFirmaIdDto id;
    TbFirmaDto idtbFirma;
    TbEventoFormatoCertificadoDto tbEventoFormatoCertificadoIdtbEvento;
}