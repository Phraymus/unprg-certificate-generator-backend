package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class TbEventoFormatoCertificadoId implements Serializable {
    private static final long serialVersionUID = 2262779215907498587L;
    @Column(name = "idtb_evento", nullable = false)
    private Integer idTbEvento;

    @Column(name = "idtb_formato_certificado", nullable = false)
    private Integer idTbFormatoCertificado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbEventoFormatoCertificadoId entity = (TbEventoFormatoCertificadoId) o;
        return Objects.equals(this.idTbEvento, entity.idTbEvento) &&
                Objects.equals(this.idTbFormatoCertificado, entity.idTbFormatoCertificado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTbEvento, idTbFormatoCertificado);
    }

}