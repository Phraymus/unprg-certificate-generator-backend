package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbPersona;
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
public class TbPersonaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String telefono;
    private String email;

    public static TbPersonaDto build() {
        return TbPersonaDto.builder().build();
    }

    public TbPersonaDto fromEntity(TbPersonaDto template, TbPersona entity) {
        if (entity != null) {
            return TbPersonaDto.builder()
                    .id(entity.getId())
                    .nombres(entity.getNombres())
                    .apellidoPaterno(entity.getApellidoPaterno())
                    .apellidoMaterno(entity.getApellidoMaterno())
                    .dni(entity.getDni())
                    .telefono(entity.getTelefono())
                    .email(entity.getEmail())
                    .build();
        } else {
            return null;
        }
    }

    public TbPersonaDto fromProxy(TbPersonaDto template, TbPersonaDto entity) {
        if (entity != null) {
            return TbPersonaDto.builder()
                    .id(entity.getId())
                    .nombres(entity.getNombres())
                    .apellidoPaterno(entity.getApellidoPaterno())
                    .apellidoMaterno(entity.getApellidoMaterno())
                    .dni(entity.getDni())
                    .telefono(entity.getTelefono())
                    .email(entity.getEmail())
                    .build();
        } else {
            return null;
        }
    }

    public TbPersonaDto fromEntity(TbPersona entity) {
        return fromEntity(this, entity);
    }

    public TbPersona toEntity() {
        return TbPersona.builder()
                .id(this.getId())
                .nombres(this.getNombres())
                .apellidoPaterno(this.getApellidoPaterno())
                .apellidoMaterno(this.getApellidoMaterno())
                .dni(this.getDni())
                .telefono(this.getTelefono())
                .email(this.getEmail())
                .build();
    }
}