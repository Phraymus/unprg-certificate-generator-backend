package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_evento_formato_certificado", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_evento_formato_certificado_tb_evento1_idx", columnList = "idtb_evento"),
        @Index(name = "fk_tb_evento_formato_certificado_tb_formato_certificado1_idx", columnList = "idtb_formato_certificado")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbEventoFormatoCertificado {
    @EmbeddedId
    private TbEventoFormatoCertificadoId id;

    @MapsId("idTbEvento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_evento", nullable = false)
    private TbEvento tbEvento;

    @MapsId("idTbFormatoCertificado")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_formato_certificado", nullable = false)
    private TbFormatoCertificado tbFormatoCertificado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_tipo_participante", nullable = false)
    private TbTipoParticipante tbTipoParticipante;

}