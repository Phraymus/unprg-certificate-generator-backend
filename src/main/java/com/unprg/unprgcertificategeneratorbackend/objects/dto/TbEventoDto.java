package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TbEventoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigo;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public static TbEventoDto build() {
        return TbEventoDto.builder().build();
    }

    public TbEventoDto fromEntity(TbEventoDto template, TbEvento entity) {
        if (entity != null) {
            return TbEventoDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombre(entity.getNombre())
                    .fechaInicio(entity.getFechaInicio())
                    .fechaFin(entity.getFechaFin())
                    .build();
        } else {
            return null;
        }
    }

    public TbEventoDto fromProxy(TbEventoDto template, TbEventoDto entity) {
        if (entity != null) {
            return TbEventoDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombre(entity.getNombre())
                    .fechaInicio(entity.getFechaInicio())
                    .fechaFin(entity.getFechaFin())
                    .build();
        } else {
            return null;
        }
    }

    public TbEventoDto fromEntity(TbEvento entity) {
        return fromEntity(this, entity);
    }

    public TbEvento toEntity() {
        return TbEvento.builder()
                .id(this.getId())
                .codigo(this.getCodigo())
                .nombre(this.getNombre())
                .fechaInicio(this.getFechaInicio())
                .fechaFin(this.getFechaFin())
                .build();
    }
}