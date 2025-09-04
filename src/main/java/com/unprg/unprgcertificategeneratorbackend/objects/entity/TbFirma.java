package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "idtb_firma", nullable = false)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 45)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

}