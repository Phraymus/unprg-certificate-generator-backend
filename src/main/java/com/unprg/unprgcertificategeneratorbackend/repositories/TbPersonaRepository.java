package com.unprg.unprgcertificategeneratorbackend.repositories;

import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TbPersonaRepository extends JpaRepository<TbPersona, Integer> {

    // Consulta personalizada con LIKE para los 4 campos usando un solo parámetro
    @Query("SELECT t FROM TbPersona t WHERE " +
            "(t.nombres LIKE %:param% OR t.apellidoPaterno LIKE %:param% OR t.apellidoMaterno LIKE %:param% OR t.dni LIKE %:param%)")
    List<TbPersona> findAllByNombresOrApellidoPaternoOrApellidoMaternoOrDniLike(@Param("param") String param);
}
