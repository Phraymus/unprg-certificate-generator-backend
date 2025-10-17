package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_firma", schema = "db_unprg_certificate")
@NoArgsConstructor
@AllArgsConstructor
public class TbFirma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtb_firma", nullable = false)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 45)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    @Column(name = "cargo", nullable = false, length = 1)
    private String cargo;

    @Column(name = "entidad", nullable = false, length = 1)
    private String entidad;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;
}