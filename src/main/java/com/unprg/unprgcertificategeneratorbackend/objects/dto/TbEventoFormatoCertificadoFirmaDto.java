package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificadoFirma;
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
public class TbEventoFormatoCertificadoFirmaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private TbEventoFormatoCertificadoFirmaIdDto id;
    private TbFirmaDto idtbFirma;
    private TbEventoFormatoCertificadoDto tbEventoFormatoCertificadoIdtbEvento;

    @JsonIgnore
    @Builder.Default
    private TbEventoFormatoCertificadoFirmaIdDto defId = null;

    @JsonIgnore
    @Builder.Default
    private TbFirmaDto defIdtbFirma = null;

    @JsonIgnore
    @Builder.Default
    private TbEventoFormatoCertificadoDto defTbEventoFormatoCertificadoIdtbEvento = null;

    public static TbEventoFormatoCertificadoFirmaDto build() {
        return TbEventoFormatoCertificadoFirmaDto.builder().build();
    }

    public TbEventoFormatoCertificadoFirmaDto fromEntity(TbEventoFormatoCertificadoFirmaDto template, TbEventoFormatoCertificadoFirma entity) {
        if (entity != null) {
            TbEventoFormatoCertificadoFirmaDto dto = TbEventoFormatoCertificadoFirmaDto.builder()
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbEventoFormatoCertificadoFirmaIdDto.build().fromEntity(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefIdtbFirma() != null) {
                dto.setIdtbFirma(TbFirmaDto.build().fromEntity(template.getDefIdtbFirma(), entity.getIdtbFirma()));
                dto.setDefIdtbFirma(template.getDefIdtbFirma());
            }
            if (template.getDefTbEventoFormatoCertificadoIdtbEvento() != null) {
                dto.setTbEventoFormatoCertificadoIdtbEvento(TbEventoFormatoCertificadoDto.build().fromEntity(template.getDefTbEventoFormatoCertificadoIdtbEvento(), entity.getTbEventoFormatoCertificadoIdtbEvento()));
                dto.setDefTbEventoFormatoCertificadoIdtbEvento(template.getDefTbEventoFormatoCertificadoIdtbEvento());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoFirmaDto fromProxy(TbEventoFormatoCertificadoFirmaDto template, TbEventoFormatoCertificadoFirmaDto entity) {
        if (entity != null) {
            TbEventoFormatoCertificadoFirmaDto dto = TbEventoFormatoCertificadoFirmaDto.builder()
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbEventoFormatoCertificadoFirmaIdDto.build().fromProxy(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefIdtbFirma() != null) {
                dto.setIdtbFirma(TbFirmaDto.build().fromProxy(template.getDefIdtbFirma(), entity.getIdtbFirma()));
                dto.setDefIdtbFirma(template.getDefIdtbFirma());
            }
            if (template.getDefTbEventoFormatoCertificadoIdtbEvento() != null) {
                dto.setTbEventoFormatoCertificadoIdtbEvento(TbEventoFormatoCertificadoDto.build().fromProxy(template.getDefTbEventoFormatoCertificadoIdtbEvento(), entity.getTbEventoFormatoCertificadoIdtbEvento()));
                dto.setDefTbEventoFormatoCertificadoIdtbEvento(template.getDefTbEventoFormatoCertificadoIdtbEvento());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoFirmaDto fromEntity(TbEventoFormatoCertificadoFirma entity) {
        return fromEntity(this, entity);
    }

    public TbEventoFormatoCertificadoFirma toEntity() {
        return TbEventoFormatoCertificadoFirma.builder()
                .id(this.getId() != null ? this.getId().toEntity() : null)
                .idtbFirma(this.getIdtbFirma() != null ? this.getIdtbFirma().toEntity() : null)
                .tbEventoFormatoCertificadoIdtbEvento(this.getTbEventoFormatoCertificadoIdtbEvento() != null ? this.getTbEventoFormatoCertificadoIdtbEvento().toEntity() : null)
                .build();
    }
}