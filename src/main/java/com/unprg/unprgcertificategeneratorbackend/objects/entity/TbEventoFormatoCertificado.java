package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_evento_formato_certificado", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_evento_formato_certificado_tb_formato_certificado1_idx", columnList = "idtb_formato_certificado")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbEventoFormatoCertificado {
    @Id
    @Column(name = "idtb_evento", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_evento", nullable = false)
    private TbEvento tbEvento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_formato_certificado", nullable = false)
    private TbFormatoCertificado idtbFormatoCertificado;

    @Column(name = "codigo", nullable = false, length = 45)
    private String codigo;

    @Column(name = "descripcion", nullable = false, length = 45)
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

}