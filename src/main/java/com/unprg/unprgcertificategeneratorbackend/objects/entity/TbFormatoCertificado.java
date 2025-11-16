package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_formato_certificado", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_formato_certificado_tb_usuario1_idx", columnList = "idtb_usuario")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbFormatoCertificado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtb_formato_certificado", nullable = false)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 45)
    private String codigo;

    @Column(name = "nombre_formato", nullable = false, length = 45)
    private String nombreFormato;

    @Column(name = "ruta_formato", nullable = false, length = 200) // AUMENTADO: para rutas largas
    private String rutaFormato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idtb_usuario", nullable = false)
    private TbUsuario tbUsuario;
}