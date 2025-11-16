package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoIdDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbEventoFormatoCertificadoService extends GenericCrud<TbEventoFormatoCertificadoDto, TbEventoFormatoCertificadoIdDto> {
    List<TbEventoFormatoCertificadoDto> findAll();
}