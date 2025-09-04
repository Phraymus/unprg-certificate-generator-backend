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
public class TbParticipanteId implements Serializable {
    private static final long serialVersionUID = 2287453503612582868L;
    @Column(name = "idtb_evento", nullable = false)
    private Integer idtbEvento;

    @Column(name = "idtb_persona", nullable = false)
    private Integer idtbPersona;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbParticipanteId entity = (TbParticipanteId) o;
        return Objects.equals(this.idtbPersona, entity.idtbPersona) &&
                Objects.equals(this.idtbEvento, entity.idtbEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtbPersona, idtbEvento);
    }

}