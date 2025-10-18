package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipanteId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TbParticipanteIdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idtbEvento;
    private Integer idtbPersona;

    public static TbParticipanteIdDto build() {
        return TbParticipanteIdDto.builder().build();
    }

    public TbParticipanteIdDto fromEntity(TbParticipanteIdDto template, TbParticipanteId entity) {
        if (entity != null) {
            return TbParticipanteIdDto.builder()
                    .idtbEvento(entity.getIdtbEvento())
                    .idtbPersona(entity.getIdtbPersona())
                    .build();
        } else {
            return null;
        }
    }

    public TbParticipanteIdDto fromProxy(TbParticipanteIdDto template, TbParticipanteIdDto entity) {
        if (entity != null) {
            return TbParticipanteIdDto.builder()
                    .idtbEvento(entity.getIdtbEvento())
                    .idtbPersona(entity.getIdtbPersona())
                    .build();
        } else {
            return null;
        }
    }

    public TbParticipanteIdDto fromEntity(TbParticipanteId entity) {
        return fromEntity(this, entity);
    }

    public TbParticipanteId toEntity() {
        return TbParticipanteId.builder()
                .idtbEvento(this.getIdtbEvento())
                .idtbPersona(this.getIdtbPersona())
                .build();
    }
}