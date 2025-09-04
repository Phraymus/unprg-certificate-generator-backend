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
public class TbEventoFormatoCertificadoFirmaId implements Serializable {
    private static final long serialVersionUID = 2262779215907498586L;
    @Column(name = "idtb_firma", nullable = false)
    private Integer idtbFirma;

    @Column(name = "tb_evento_formato_certificado_idtb_evento", nullable = false)
    private Integer tbEventoFormatoCertificadoIdtbEvento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbEventoFormatoCertificadoFirmaId entity = (TbEventoFormatoCertificadoFirmaId) o;
        return Objects.equals(this.idtbFirma, entity.idtbFirma) &&
                Objects.equals(this.tbEventoFormatoCertificadoIdtbEvento, entity.tbEventoFormatoCertificadoIdtbEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtbFirma, tbEventoFormatoCertificadoIdtbEvento);
    }

}