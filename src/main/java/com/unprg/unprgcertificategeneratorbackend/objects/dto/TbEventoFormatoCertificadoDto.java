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
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TbEventoFormatoCertificadoDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private TbEventoDto tbEvento;
    private TbFormatoCertificadoDto idtbFormatoCertificado;
    private String codigo;
    private String descripcion;
    private LocalDate fecha;

    @JsonIgnore
    @Builder.Default
    private TbEventoDto defTbEvento = null;

    @JsonIgnore
    @Builder.Default
    private TbFormatoCertificadoDto defIdtbFormatoCertificado = null;

    public static TbEventoFormatoCertificadoDto build() {
        return TbEventoFormatoCertificadoDto.builder().build();
    }

    public TbEventoFormatoCertificadoDto fromEntity(TbEventoFormatoCertificadoDto template, TbEventoFormatoCertificado entity) {
        if (entity != null) {
            TbEventoFormatoCertificadoDto dto = TbEventoFormatoCertificadoDto.builder()
                    .id(entity.getId())
                    .codigo(entity.getCodigo())
                    .descripcion(entity.getDescripcion())
                    .fecha(entity.getFecha())
                    .build();

            if (template.getDefTbEvento() != null) {
                dto.setTbEvento(TbEventoDto.build().fromEntity(template.getDefTbEvento(), entity.getTbEvento()));
                dto.setDefTbEvento(template.getDefTbEvento());
            }
            if (template.getDefIdtbFormatoCertificado() != null) {
                dto.setIdtbFormatoCertificado(TbFormatoCertificadoDto.build().fromEntity(template.getDefIdtbFormatoCertificado(), entity.getIdtbFormatoCertificado()));
                dto.setDefIdtbFormatoCertificado(template.getDefIdtbFormatoCertificado());
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
                    .codigo(entity.getCodigo())
                    .descripcion(entity.getDescripcion())
                    .fecha(entity.getFecha())
                    .build();

            if (template.getDefTbEvento() != null) {
                dto.setTbEvento(TbEventoDto.build().fromProxy(template.getDefTbEvento(), entity.getTbEvento()));
                dto.setDefTbEvento(template.getDefTbEvento());
            }
            if (template.getDefIdtbFormatoCertificado() != null) {
                dto.setIdtbFormatoCertificado(TbFormatoCertificadoDto.build().fromProxy(template.getDefIdtbFormatoCertificado(), entity.getIdtbFormatoCertificado()));
                dto.setDefIdtbFormatoCertificado(template.getDefIdtbFormatoCertificado());
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
                .id(this.getId())
                .codigo(this.getCodigo())
                .descripcion(this.getDescripcion())
                .fecha(this.getFecha())
                .tbEvento(this.getTbEvento() != null ? this.getTbEvento().toEntity() : null)
                .idtbFormatoCertificado(this.getIdtbFormatoCertificado() != null ? this.getIdtbFormatoCertificado().toEntity() : null)
                .build();
    }
}