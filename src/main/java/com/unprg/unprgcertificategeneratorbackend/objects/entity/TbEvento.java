package com.unprg.unprgcertificategeneratorbackend.objects.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_evento", schema = "db_unprg_certificate")
@NoArgsConstructor
@AllArgsConstructor
public class TbEvento {
    @Id
    @Column(name = "idtb_evento", nullable = false)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 45)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

}