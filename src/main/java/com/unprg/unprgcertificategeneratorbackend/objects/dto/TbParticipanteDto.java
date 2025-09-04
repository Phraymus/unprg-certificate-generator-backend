package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipante}
 */
@Data
@Builder
@Value
public class TbParticipanteDto implements Serializable {
    TbParticipanteIdDto id;
    TbEventoDto idtbEvento;
    TbPersonaDto idtbPersona;
    String estado;
    String fechaInscripcion;
    BigDecimal nota;
}