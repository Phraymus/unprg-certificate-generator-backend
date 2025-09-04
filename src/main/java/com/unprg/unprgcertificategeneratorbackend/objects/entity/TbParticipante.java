package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_participante", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_participante_tb_evento1_idx", columnList = "idtb_evento"),
        @Index(name = "fk_tb_participante_tb_persona1_idx", columnList = "idtb_persona")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbParticipante {
    @EmbeddedId
    private TbParticipanteId id;

    @MapsId("idtbEvento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_evento", nullable = false)
    private TbEvento idtbEvento;

    @MapsId("idtbPersona")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_persona", nullable = false)
    private TbPersona idtbPersona;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    @Column(name = "fecha_inscripcion", nullable = false, length = 45)
    private String fechaInscripcion;

    @Column(name = "nota", precision = 2)
    private BigDecimal nota;

}