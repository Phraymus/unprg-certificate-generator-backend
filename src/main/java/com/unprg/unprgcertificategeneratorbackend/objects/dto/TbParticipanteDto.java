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
    private TbEventoDto idtbEvento;
    private TbPersonaDto idtbPersona;
    private String estado;
    private String fechaInscripcion;
    private BigDecimal nota;

    @JsonIgnore
    @Builder.Default
    private TbParticipanteIdDto defId = null;

    @JsonIgnore
    @Builder.Default
    private TbEventoDto defIdtbEvento = null;

    @JsonIgnore
    @Builder.Default
    private TbPersonaDto defIdtbPersona = null;

    public static TbParticipanteDto build() {
        return TbParticipanteDto.builder().build();
    }

    public TbParticipanteDto fromEntity(TbParticipanteDto template, TbParticipante entity) {
        if (entity != null) {
            TbParticipanteDto dto = TbParticipanteDto.builder()
                    .estado(entity.getEstado())
                    .fechaInscripcion(entity.getFechaInscripcion())
                    .nota(entity.getNota())
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbParticipanteIdDto.build().fromEntity(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefIdtbEvento() != null) {
                dto.setIdtbEvento(TbEventoDto.build().fromEntity(template.getDefIdtbEvento(), entity.getIdtbEvento()));
                dto.setDefIdtbEvento(template.getDefIdtbEvento());
            }
            if (template.getDefIdtbPersona() != null) {
                dto.setIdtbPersona(TbPersonaDto.build().fromEntity(template.getDefIdtbPersona(), entity.getIdtbPersona()));
                dto.setDefIdtbPersona(template.getDefIdtbPersona());
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
                    .build();

            if (template.getDefId() != null) {
                dto.setId(TbParticipanteIdDto.build().fromProxy(template.getDefId(), entity.getId()));
                dto.setDefId(template.getDefId());
            }
            if (template.getDefIdtbEvento() != null) {
                dto.setIdtbEvento(TbEventoDto.build().fromProxy(template.getDefIdtbEvento(), entity.getIdtbEvento()));
                dto.setDefIdtbEvento(template.getDefIdtbEvento());
            }
            if (template.getDefIdtbPersona() != null) {
                dto.setIdtbPersona(TbPersonaDto.build().fromProxy(template.getDefIdtbPersona(), entity.getIdtbPersona()));
                dto.setDefIdtbPersona(template.getDefIdtbPersona());
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
                .idtbEvento(this.getIdtbEvento() != null ? this.getIdtbEvento().toEntity() : null)
                .idtbPersona(this.getIdtbPersona() != null ? this.getIdtbPersona().toEntity() : null)
                .build();
    }
}