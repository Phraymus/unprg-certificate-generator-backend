package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificadoId;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TbEventoFormatoCertificadoIdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idtbEvento;
    private Integer idtbFormatoCertificado;

    public static TbEventoFormatoCertificadoIdDto build() {
        return TbEventoFormatoCertificadoIdDto.builder().build();
    }

    public TbEventoFormatoCertificadoIdDto fromEntity(TbEventoFormatoCertificadoIdDto template, TbEventoFormatoCertificadoId entity) {
        if (entity != null) {
            return TbEventoFormatoCertificadoIdDto.builder()
                    .idtbEvento(entity.getIdTbEvento())
                    .idtbFormatoCertificado(entity.getIdTbFormatoCertificado())
                    .build();
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoIdDto fromProxy(TbEventoFormatoCertificadoIdDto template, TbEventoFormatoCertificadoIdDto entity) {
        if (entity != null) {
            return TbEventoFormatoCertificadoIdDto.builder()
                    .idtbEvento(entity.getIdtbEvento())
                    .idtbFormatoCertificado(entity.getIdtbFormatoCertificado())
                    .build();
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoIdDto fromEntity(TbEventoFormatoCertificadoId entity) {
        return fromEntity(this, entity);
    }

    public TbEventoFormatoCertificadoId toEntity() {
        return TbEventoFormatoCertificadoId.builder()
                .idTbEvento(this.getIdtbEvento())
                .idTbFormatoCertificado(this.getIdtbFormatoCertificado())
                .build();
    }
}