package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipanteId}
 */
@Data
@Builder
@Value
public class TbParticipanteIdDto implements Serializable {
    Integer idtbEvento;
    Integer idtbPersona;
}