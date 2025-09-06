package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_usuario", schema = "db_unprg_certificate", indexes = {
        @Index(name = "fk_tb_usuario_tb_persona_idx", columnList = "idtb_persona")
})
@NoArgsConstructor
@AllArgsConstructor
public class TbUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtb_usuario", nullable = false)
    private Integer id;

    @Column(name = "usuario", nullable = false, length = 45)
    private String usuario;

    @Column(name = "clave", nullable = false, length = 45)
    private String clave;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "idtb_persona", nullable = false)
    private TbPersona idtbPersona;

}