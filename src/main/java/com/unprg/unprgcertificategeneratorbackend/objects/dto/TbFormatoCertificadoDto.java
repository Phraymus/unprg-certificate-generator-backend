package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificado;
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
public class TbFormatoCertificadoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigo;
    private String nombreFormato;
    private String rutaFormato;
    private TbUsuarioDto idtbUsuario;

    @JsonIgnore
    @Builder.Default
    private TbUsuarioDto defIdtbUsuario = null;

    public static TbFormatoCertificadoDto build() {
        return TbFormatoCertificadoDto.builder().build();
    }

    public TbFormatoCertificadoDto fromEntity(TbFormatoCertificadoDto template, TbFormatoCertificado entity) {
        if (entity != null) {
            TbFormatoCertificadoDto dto = TbFormatoCertificadoDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombreFormato(entity.getNombreFormato())
                    .rutaFormato(entity.getRutaFormato())
                    .build();

            if (template.getDefIdtbUsuario() != null) {
                dto.setIdtbUsuario(TbUsuarioDto.build().fromEntity(template.getDefIdtbUsuario(), entity.getIdtbUsuario()));
                dto.setDefIdtbUsuario(template.getDefIdtbUsuario());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbFormatoCertificadoDto fromProxy(TbFormatoCertificadoDto template, TbFormatoCertificadoDto entity) {
        if (entity != null) {
            TbFormatoCertificadoDto dto = TbFormatoCertificadoDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .nombreFormato(entity.getNombreFormato())
                    .rutaFormato(entity.getRutaFormato())
                    .build();

            if (template.getDefIdtbUsuario() != null) {
                dto.setIdtbUsuario(TbUsuarioDto.build().fromProxy(template.getDefIdtbUsuario(), entity.getIdtbUsuario()));
                dto.setDefIdtbUsuario(template.getDefIdtbUsuario());
            }
            return dto;
        } else {
            return null;
        }
    }

    public TbFormatoCertificadoDto fromEntity(TbFormatoCertificado entity) {
        return fromEntity(this, entity);
    }

    public TbFormatoCertificado toEntity() {
        return TbFormatoCertificado.builder()
                .id(this.getId())
                .codigo(this.getCodigo())
                .nombreFormato(this.getNombreFormato())
                .rutaFormato(this.getRutaFormato())
                .idtbUsuario(this.getIdtbUsuario() != null ? this.getIdtbUsuario().toEntity() : null)
                .build();
    }
}