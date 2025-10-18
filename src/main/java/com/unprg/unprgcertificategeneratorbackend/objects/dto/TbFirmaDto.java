package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFirma;
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
public class TbFirmaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigo;
    private String nombre;
    private String estado;
    private String cargo;
    private String entidad;
    private byte[] imagen;

    public static TbFirmaDto build() {
        return TbFirmaDto.builder().build();
    }

    public TbFirmaDto fromEntity(TbFirmaDto template, TbFirma entity) {
        if (entity != null) {
            return TbFirmaDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombre(entity.getNombre())
                    .estado(entity.getEstado())
                    .cargo(entity.getCargo())
                    .entidad(entity.getEntidad())
                    .imagen(entity.getImagen())
                    .build();
        } else {
            return null;
        }
    }

    public TbFirmaDto fromProxy(TbFirmaDto template, TbFirmaDto entity) {
        if (entity != null) {
            return TbFirmaDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombre(entity.getNombre())
                    .estado(entity.getEstado())
                    .cargo(entity.getCargo())
                    .entidad(entity.getEntidad())
                    .imagen(entity.getImagen())
                    .build();
        } else {
            return null;
        }
    }

    public TbFirmaDto fromEntity(TbFirma entity) {
        return fromEntity(this, entity);
    }

    public TbFirma toEntity() {
        return TbFirma.builder()
                .id(this.getId())
                .codigo(this.getCodigo())
                .nombre(this.getNombre())
                .estado(this.getEstado())
                .cargo(this.getCargo())
                .entidad(this.getEntidad())
                .imagen(this.getImagen())
                .build();
    }
}