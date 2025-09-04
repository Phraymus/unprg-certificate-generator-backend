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
@Table(name = "tb_persona", schema = "db_unprg_certificate")
@NoArgsConstructor
@AllArgsConstructor
public class TbPersona {
    @Id
    @Column(name = "idtb_persona", nullable = false)
    private Integer id;

    @Column(name = "nombres", nullable = false, length = 45)
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 45)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false, length = 45)
    private String apellidoMaterno;

    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

}