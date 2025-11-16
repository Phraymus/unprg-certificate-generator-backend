package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificado;
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
public class TbEventoFormatoCertificadoDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    private TbEventoFormatoCertificadoIdDto id;
    private TbEventoDto tbEvento;
    private TbFormatoCertificadoDto tbFormatoCertificado;
    private TbTipoParticipanteDto tbTipoParticipante;

    @JsonIgnore
    @Builder.Default
    private TbEventoFormatoCertificadoIdDto defId = null;

    @JsonIgnore
    @Builder.Default
    private TbEventoDto defTbEvento = null;

    @JsonIgnore
    @Builder.Default
    private TbFormatoCertificadoDto defTbFormatoCertificado = null;

    @JsonIgnore
    @Builder.Default
    private TbTipoParticipanteDto defTbTipoParticipante = null;

    public static TbEventoFormatoCertificadoDto build() {
        return TbEventoFormatoCertificadoDto.builder().build();
    }

    public TbEventoFormatoCertificadoDto fromEntity(TbEventoFormatoCertificadoDto template, TbEventoFormatoCertificado entity) {
        if (entity != null) {
            TbEventoFormatoCertificadoDto dto = TbEventoFormatoCertificadoDto.builder()
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbEventoFormatoCertificadoIdDto.build().fromEntity(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }

            if (template.getDefTbEvento() != null) {
                dto.setTbEvento(TbEventoDto.build().fromEntity(template.getDefTbEvento(), entity.getTbEvento()));
                dto.setDefTbEvento(template.getDefTbEvento());
            }
            if (template.getDefTbFormatoCertificado() != null) {
                dto.setTbFormatoCertificado(TbFormatoCertificadoDto.build().fromEntity(template.getDefTbFormatoCertificado(), entity.getTbFormatoCertificado()));
                dto.setDefTbFormatoCertificado(template.getDefTbFormatoCertificado());
            }
            if (template.getDefTbTipoParticipante() != null) {
                dto.setTbTipoParticipante(TbTipoParticipanteDto.build().fromEntity(template.getDefTbTipoParticipante(), entity.getTbTipoParticipante()));
                dto.setDefTbTipoParticipante(template.getDefTbTipoParticipante());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoDto fromProxy(TbEventoFormatoCertificadoDto template, TbEventoFormatoCertificadoDto entity) {
        if (entity != null) {
            TbEventoFormatoCertificadoDto dto = TbEventoFormatoCertificadoDto.builder()
                    .id(entity.getId())
                    .build();

            if (template.getDefTbEvento() != null) {
                dto.setTbEvento(TbEventoDto.build().fromProxy(template.getDefTbEvento(), entity.getTbEvento()));
                dto.setDefTbEvento(template.getDefTbEvento());
            }
            if (template.getDefTbFormatoCertificado() != null) {
                dto.setTbFormatoCertificado(TbFormatoCertificadoDto.build().fromProxy(template.getDefTbFormatoCertificado(), entity.getTbFormatoCertificado()));
                dto.setDefTbFormatoCertificado(template.getDefTbFormatoCertificado());
            }
            if (template.getDefTbTipoParticipante() != null) {
                dto.setTbTipoParticipante(TbTipoParticipanteDto.build().fromProxy(template.getDefTbTipoParticipante(), entity.getTbTipoParticipante()));
                dto.setDefTbTipoParticipante(template.getDefTbTipoParticipante());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbEventoFormatoCertificadoDto fromEntity(TbEventoFormatoCertificado entity) {
        return fromEntity(this, entity);
    }

    public TbEventoFormatoCertificado toEntity() {
        return TbEventoFormatoCertificado.builder()
                .id(this.getId() != null ? this.getId().toEntity() : null)
                .tbEvento(this.getTbEvento() != null ? this.getTbEvento().toEntity() : null)
                .tbFormatoCertificado(this.getTbFormatoCertificado() != null ? this.getTbFormatoCertificado().toEntity() : null)
                .tbTipoParticipante(this.tbTipoParticipante != null ? this.tbTipoParticipante.toEntity() : null)
                .build();
    }
}