package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificadoFirmaId;
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
public class TbFormatoCertificadoFirmaIdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idtbFirma;
    private Integer idtbFormatoCertificado;

    public static TbFormatoCertificadoFirmaIdDto build() {
        return TbFormatoCertificadoFirmaIdDto.builder().build();
    }

    public TbFormatoCertificadoFirmaIdDto fromEntity(TbFormatoCertificadoFirmaIdDto template, TbFormatoCertificadoFirmaId entity) {
        if (entity != null) {
            return TbFormatoCertificadoFirmaIdDto.builder()
                    .idtbFirma(entity.getIdTbFirma())
                    .idtbFormatoCertificado(entity.getIdTbFormatoCertificado())
                    .build();
        } else {
            return null;
        }
    }

    public TbFormatoCertificadoFirmaIdDto fromProxy(TbFormatoCertificadoFirmaIdDto template, TbFormatoCertificadoFirmaIdDto entity) {
        if (entity != null) {
            return TbFormatoCertificadoFirmaIdDto.builder()
                    .idtbFirma(entity.getIdtbFirma())
                    .idtbFormatoCertificado(entity.getIdtbFormatoCertificado())
                    .build();
        } else {
            return null;
        }
    }

    public TbFormatoCertificadoFirmaIdDto fromEntity(TbFormatoCertificadoFirmaId entity) {
        return fromEntity(this, entity);
    }

    public TbFormatoCertificadoFirmaId toEntity() {
        return TbFormatoCertificadoFirmaId.builder()
                .idTbFirma(this.getIdtbFirma())
                .idTbFormatoCertificado(this.getIdtbFormatoCertificado())
                .build();
    }
}