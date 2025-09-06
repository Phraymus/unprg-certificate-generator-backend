package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_certificado", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_certificado_tb_participante1_idx", columnList = "idtb_evento, idtb_persona")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbCertificado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtb_certificado", nullable = false)
    private Integer id;

    @Column(name = "codigo_qr", nullable = false, length = 45)
    private String codigoQr;

    @Column(name = "url_certificado", nullable = false, length = 45)
    private String urlCertificado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "idtb_evento", referencedColumnName = "idtb_evento", nullable = false),
            @JoinColumn(name = "idtb_persona", referencedColumnName = "idtb_persona", nullable = false)
    })
    private TbParticipante tbParticipante;

}