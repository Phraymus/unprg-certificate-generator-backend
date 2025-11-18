package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbTipoParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbTipoParticipanteService extends GenericCrud<TbTipoParticipanteDto,Integer> {
    List<TbTipoParticipanteDto> findAll();

    List<TbTipoParticipanteDto> findAllByEstado(Boolean estado);
}
