package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_evento_formato_certificado_firma", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_evento_formato_certificado_firma_tb_firma1_idx", columnList = "idtb_firma"),
        @Index(name = "fk_tb_evento_formato_certificado_firma_tb_evento_formato_ce_idx", columnList = "tb_evento_formato_certificado_idtb_evento")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbEventoFormatoCertificadoFirma {
    @EmbeddedId
    private TbEventoFormatoCertificadoFirmaId id;

    @MapsId("idtbFirma")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_firma", nullable = false)
    private TbFirma idtbFirma;

    @MapsId("tbEventoFormatoCertificadoIdtbEvento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tb_evento_formato_certificado_idtb_evento", nullable = false)
    private TbEventoFormatoCertificado tbEventoFormatoCertificadoIdtbEvento;

}