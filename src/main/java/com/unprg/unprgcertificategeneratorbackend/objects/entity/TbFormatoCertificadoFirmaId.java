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
public class TbFormatoCertificadoFirmaId implements Serializable {
    private static final long serialVersionUID = 2262779215907498586L;
    @Column(name = "idtb_firma", nullable = false)
    private Integer idtbFirma;

    @Column(name = "idtb_formato_certificado", nullable = false)
    private Integer idtbFormatoCertificado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbFormatoCertificadoFirmaId entity = (TbFormatoCertificadoFirmaId) o;
        return Objects.equals(this.idtbFirma, entity.idtbFirma) &&
                Objects.equals(this.idtbFormatoCertificado, entity.idtbFormatoCertificado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtbFirma, idtbFormatoCertificado);
    }

}