package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteIdDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbParticipanteService extends GenericCrud<TbParticipanteDto, TbParticipanteIdDto> {
    List<TbParticipanteDto> findAll();

    List<TbParticipanteDto> findAllByIdEvento(Integer k);

}