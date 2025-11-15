package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificadoFirma;
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
public class TbFormatoCertificadoFirmaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private TbFormatoCertificadoFirmaIdDto id;
    private TbFirmaDto tbFirma;
    private TbFormatoCertificadoDto tbFormatoCertificado;

    @JsonIgnore
    @Builder.Default
    private TbFormatoCertificadoFirmaIdDto defId = null;

    @JsonIgnore
    @Builder.Default
    private TbFirmaDto defTbFirma = null;

    @JsonIgnore
    @Builder.Default
    private TbFormatoCertificadoDto defTbFormatoCertificado = null;

    public static TbFormatoCertificadoFirmaDto build() {
        return TbFormatoCertificadoFirmaDto.builder().build();
    }

    public TbFormatoCertificadoFirmaDto fromEntity(TbFormatoCertificadoFirmaDto template, TbFormatoCertificadoFirma entity) {
        if (entity != null) {
            TbFormatoCertificadoFirmaDto dto = TbFormatoCertificadoFirmaDto.builder()
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbFormatoCertificadoFirmaIdDto.build().fromEntity(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefTbFirma() != null) {
                dto.setTbFirma(TbFirmaDto.build().fromEntity(template.getDefTbFirma(), entity.getTbFirma()));
                dto.setDefTbFirma(template.getDefTbFirma());
            }
            if (template.getDefTbFormatoCertificado() != null) {
                dto.setTbFormatoCertificado(TbFormatoCertificadoDto.build().fromEntity(template.getDefTbFormatoCertificado(), entity.getTbFormatoCertificado()));
                dto.setDefTbFormatoCertificado(template.getDefTbFormatoCertificado());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbFormatoCertificadoFirmaDto fromProxy(TbFormatoCertificadoFirmaDto template, TbFormatoCertificadoFirmaDto entity) {
        if (entity != null) {
            TbFormatoCertificadoFirmaDto dto = TbFormatoCertificadoFirmaDto.builder()
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbFormatoCertificadoFirmaIdDto.build().fromProxy(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefTbFirma() != null) {
                dto.setTbFirma(TbFirmaDto.build().fromProxy(template.getDefTbFirma(), entity.getTbFirma()));
                dto.setDefTbFirma(template.getDefTbFirma());
            }
            if (template.getDefTbFormatoCertificado() != null) {
                dto.setTbFormatoCertificado(TbFormatoCertificadoDto.build().fromProxy(template.getDefTbFormatoCertificado(), entity.getTbFormatoCertificado()));
                dto.setDefTbFormatoCertificado(template.getDefTbFormatoCertificado());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbFormatoCertificadoFirmaDto fromEntity(TbFormatoCertificadoFirma entity) {
        return fromEntity(this, entity);
    }

    public TbFormatoCertificadoFirma toEntity() {
        return TbFormatoCertificadoFirma.builder()
                .id(this.getId() != null ? this.getId().toEntity() : null)
                .tbFirma(this.getTbFirma() != null ? this.getTbFirma().toEntity() : null)
                .tbFormatoCertificado(this.getTbFormatoCertificado() != null ? this.getTbFormatoCertificado().toEntity() : null)
                .build();
    }
}