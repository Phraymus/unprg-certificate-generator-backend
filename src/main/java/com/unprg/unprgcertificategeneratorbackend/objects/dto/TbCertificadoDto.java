package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbCertificado;
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
public class TbCertificadoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigoQr;
    private String urlCertificado;
    private TbParticipanteDto tbParticipante;

    @JsonIgnore
    @Builder.Default
    private TbParticipanteDto defTbParticipante = null;

    public static TbCertificadoDto build() {
        return TbCertificadoDto.builder().build();
    }

    public TbCertificadoDto fromEntity(TbCertificadoDto template, TbCertificado entity) {
        if (entity != null) {
            TbCertificadoDto dto = TbCertificadoDto.builder()
                    .id(entity.getId())
                    .codigoQr(entity.getCodigoQr())
                    .urlCertificado(entity.getUrlCertificado())
                    .build();

            if (template.getDefTbParticipante() != null) {
                dto.setTbParticipante(TbParticipanteDto.build().fromEntity(template.getDefTbParticipante(), entity.getTbParticipante()));
                dto.setDefTbParticipante(template.getDefTbParticipante());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbCertificadoDto fromProxy(TbCertificadoDto template, TbCertificadoDto entity) {
        if (entity != null) {
            TbCertificadoDto dto = TbCertificadoDto.builder()
                    .id(entity.getId())
                    .codigoQr(entity.getCodigoQr())
                    .urlCertificado(entity.getUrlCertificado())
                    .build();

            if (template.getDefTbParticipante() != null) {
                dto.setTbParticipante(TbParticipanteDto.build().fromProxy(template.getDefTbParticipante(), entity.getTbParticipante()));
                dto.setDefTbParticipante(template.getDefTbParticipante());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbCertificadoDto fromEntity(TbCertificado entity) {
        return fromEntity(this, entity);
    }

    public TbCertificado toEntity() {
        TbCertificado entity = TbCertificado.builder()
                .id(this.getId())
                .codigoQr(this.getCodigoQr())
                .urlCertificado(this.getUrlCertificado())
                .tbParticipante(this.getTbParticipante() != null ? this.getTbParticipante().toEntity() : null)
                .build();

        return entity;
    }
}