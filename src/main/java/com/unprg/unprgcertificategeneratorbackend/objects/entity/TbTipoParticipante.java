package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_tipo_participante", schema = "db_unprg_certificate")
@NoArgsConstructor
@AllArgsConstructor
public class TbTipoParticipante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtb_tipo_participante", nullable = false)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 45)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

}