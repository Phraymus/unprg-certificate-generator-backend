package com.unprg.unprgcertificategeneratorbackend.repositories;

import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEvento;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipante;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipanteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbParticipanteRepository extends JpaRepository<TbParticipante, TbParticipanteId> {
    List<TbParticipante> findAllByIdtbEventoId(Integer id);
}