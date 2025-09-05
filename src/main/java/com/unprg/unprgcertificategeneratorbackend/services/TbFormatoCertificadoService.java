package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbFormatoCertificadoService extends GenericCrud<TbFormatoCertificadoDto, Integer> {
    List<TbFormatoCertificadoDto> findAll();
}