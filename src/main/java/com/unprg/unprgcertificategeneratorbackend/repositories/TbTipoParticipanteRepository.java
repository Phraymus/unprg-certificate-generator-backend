package com.unprg.unprgcertificategeneratorbackend.repositories;

import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbTipoParticipante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbTipoParticipanteRepository extends JpaRepository<TbTipoParticipante,Integer> {
    List<TbTipoParticipante> findAllByEstado(String estado);
}
