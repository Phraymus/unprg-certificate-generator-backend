package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbTipoParticipante;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TbTipoParticipanteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigo;
    private String nombre;
    private String estado;

    public static TbTipoParticipanteDto build() {
        return TbTipoParticipanteDto.builder().build();
    }

    public TbTipoParticipanteDto fromEntity(TbTipoParticipanteDto template, TbTipoParticipante entity) {
        if (entity != null) {
            return TbTipoParticipanteDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombre(entity.getNombre())
                    .estado(entity.getEstado())
                    .build();
        } else {
            return null;
        }
    }

    public TbTipoParticipanteDto fromProxy(TbTipoParticipanteDto template, TbTipoParticipanteDto entity) {
        if (entity != null) {
            return TbTipoParticipanteDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombre(entity.getNombre())
                    .estado(entity.getEstado())
                    .build();
        } else {
            return null;
        }
    }

    public TbTipoParticipanteDto fromEntity(TbTipoParticipante entity) {
        return fromEntity(this, entity);
    }

    public TbTipoParticipante toEntity() {
        return TbTipoParticipante.builder()
                .id(this.getId())
                .codigo(this.getCodigo())
                .nombre(this.getNombre())
                .estado(this.getEstado())
                .build();
    }
}