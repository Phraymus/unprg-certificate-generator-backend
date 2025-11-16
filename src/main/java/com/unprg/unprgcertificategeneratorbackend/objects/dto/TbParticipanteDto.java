package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TbParticipanteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private TbParticipanteIdDto id;
    private TbEventoDto tbEvento;
    private TbPersonaDto tbPersona;
    private String estado;
    private String fechaInscripcion;
    private BigDecimal nota;
    private byte[] comprobante;
    private TbTipoParticipanteDto tbTipoParticipante;

    @JsonIgnore
    @Builder.Default
    private TbParticipanteIdDto defId = null;

    @JsonIgnore
    @Builder.Default
    private TbEventoDto defTbEvento = null;

    @JsonIgnore
    @Builder.Default
    private TbPersonaDto defTbPersona = null;

    @JsonIgnore
    @Builder.Default
    private TbTipoParticipanteDto defTbTipoParticipante = null;

    public static TbParticipanteDto build() {
        return TbParticipanteDto.builder().build();
    }

    public TbParticipanteDto fromEntity(TbParticipanteDto template, TbParticipante entity) {
        if (entity != null) {
            TbParticipanteDto dto = TbParticipanteDto.builder()
                    .estado(entity.getEstado())
                    .fechaInscripcion(entity.getFechaInscripcion())
                    .nota(entity.getNota())
                    .comprobante(entity.getComprobante())
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbParticipanteIdDto.build().fromEntity(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefTbEvento() != null) {
                dto.setTbEvento(TbEventoDto.build().fromEntity(template.getDefTbEvento(), entity.getTbEvento()));
                dto.setDefTbEvento(template.getDefTbEvento());
            }
            if (template.getDefTbPersona() != null) {
                dto.setTbPersona(TbPersonaDto.build().fromEntity(template.getDefTbPersona(), entity.getTbPersona()));
                dto.setDefTbPersona(template.getDefTbPersona());
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

    public TbParticipanteDto fromProxy(TbParticipanteDto template, TbParticipanteDto entity) {
        if (entity != null) {
            TbParticipanteDto dto = TbParticipanteDto.builder()
                    .estado(entity.getEstado())
                    .fechaInscripcion(entity.getFechaInscripcion())
                    .nota(entity.getNota())
                    .comprobante(entity.getComprobante())
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbParticipanteIdDto.build().fromProxy(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefTbEvento() != null) {
                dto.setTbEvento(TbEventoDto.build().fromProxy(template.getDefTbEvento(), entity.getTbEvento()));
                dto.setDefTbEvento(template.getDefTbEvento());
            }
            if (template.getDefTbPersona() != null) {
                dto.setTbPersona(TbPersonaDto.build().fromProxy(template.getDefTbPersona(), entity.getTbPersona()));
                dto.setDefTbPersona(template.getDefTbPersona());
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

    public TbParticipanteDto fromEntity(TbParticipante entity) {
        return fromEntity(this, entity);
    }

    public TbParticipante toEntity() {
        return TbParticipante.builder()
                .id(this.getId() != null ? this.getId().toEntity() : null)
                .estado(this.getEstado())
                .fechaInscripcion(this.getFechaInscripcion())
                .nota(this.getNota())
                .comprobante(this.getComprobante())
                .tbEvento(this.getTbEvento() != null ? this.getTbEvento().toEntity() : null)
                .tbPersona(this.getTbPersona() != null ? this.getTbPersona().toEntity() : null)
                .tbTipoParticipante(this.getTbTipoParticipante() != null ? this.getTbTipoParticipante().toEntity() : null)
                .build();
    }
}