package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_formato_certificado_firma", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_evento_formato_certificado_firma_tb_firma1_idx", columnList = "idtb_firma"),
        @Index(name = "fk_tb_evento_formato_certificado_firma_tb_formato_certificado1_idx", columnList = "idtb_formato_certificado")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbFormatoCertificadoFirma {
    @EmbeddedId
    private TbFormatoCertificadoFirmaId id;

    @MapsId("idtbFirma")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_firma", nullable = false)
    private TbFirma tbFirma;

    @MapsId("idtbFormatoCertificado")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_formato_certificado", nullable = false)
    private TbFormatoCertificado tbFormatoCertificado;

}