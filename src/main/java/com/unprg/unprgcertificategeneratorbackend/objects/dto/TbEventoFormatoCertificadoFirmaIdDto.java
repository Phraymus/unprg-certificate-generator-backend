package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificadoFirmaId;
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
public class TbEventoFormatoCertificadoFirmaIdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idtbFirma;
    private Integer tbEventoFormatoCertificadoIdtbEvento;

    public static TbEventoFormatoCertificadoFirmaIdDto build() {
        return TbEventoFormatoCertificadoFirmaIdDto.builder().build();
    }

    public TbEventoFormatoCertificadoFirmaIdDto fromEntity(TbEventoFormatoCertificadoFirmaIdDto template, TbEventoFormatoCertificadoFirmaId entity) {
        if (entity != null) {
            return TbEventoFormatoCertificadoFirmaIdDto.builder()
                    .idtbFirma(entity.getIdtbFirma())
                    .tbEventoFormatoCertificadoIdtbEvento(entity.getTbEventoFormatoCertificadoIdtbEvento())
                    .build();
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoFirmaIdDto fromProxy(TbEventoFormatoCertificadoFirmaIdDto template, TbEventoFormatoCertificadoFirmaIdDto entity) {
        if (entity != null) {
            return TbEventoFormatoCertificadoFirmaIdDto.builder()
                    .idtbFirma(entity.getIdtbFirma())
                    .tbEventoFormatoCertificadoIdtbEvento(entity.getTbEventoFormatoCertificadoIdtbEvento())
                    .build();
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoFirmaIdDto fromEntity(TbEventoFormatoCertificadoFirmaId entity) {
        return fromEntity(this, entity);
    }

    public TbEventoFormatoCertificadoFirmaId toEntity() {
        return TbEventoFormatoCertificadoFirmaId.builder()
                .idtbFirma(this.getIdtbFirma())
                .tbEventoFormatoCertificadoIdtbEvento(this.getTbEventoFormatoCertificadoIdtbEvento())
                .build();
    }
}