package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbUsuario;
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
public class TbUsuarioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String usuario;
    private String clave;
    private TbPersonaDto tbPersona;

    @JsonIgnore
    @Builder.Default
    private TbPersonaDto defTbPersona = null;

    public static TbUsuarioDto build() {
        return TbUsuarioDto.builder().build();
    }

    public TbUsuarioDto fromEntity(TbUsuarioDto template, TbUsuario entity) {
        if (entity != null) {
            TbUsuarioDto dto = TbUsuarioDto.builder()
                    .id(entity.getId())
                    .usuario(entity.getUsuario())
                    .clave(entity.getClave())
                    .build();

            if (template.getDefTbPersona() != null) {
                dto.setTbPersona(TbPersonaDto.build().fromEntity(template.getDefTbPersona(), entity.getTbPersona()));
                dto.setDefTbPersona(template.getDefTbPersona());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbUsuarioDto fromProxy(TbUsuarioDto template, TbUsuarioDto entity) {
        if (entity != null) {
            TbUsuarioDto dto = TbUsuarioDto.builder()
                    .id(entity.getId())
                    .usuario(entity.getUsuario())
                    .clave(entity.getClave())
                    .build();

            if (template.getDefTbPersona() != null) {
                dto.setTbPersona(TbPersonaDto.build().fromProxy(template.getDefTbPersona(), entity.getTbPersona()));
                dto.setDefTbPersona(template.getDefTbPersona());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbUsuarioDto fromEntity(TbUsuario entity) {
        return fromEntity(this, entity);
    }

    public TbUsuario toEntity() {
        return TbUsuario.builder()
                .id(this.getId())
                .usuario(this.getUsuario())
                .clave(this.getClave())
                .tbPersona(this.getTbPersona() != null ? this.getTbPersona().toEntity() : null)
                .build();
    }
}