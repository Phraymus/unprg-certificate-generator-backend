package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbCertificadoService extends GenericCrud<TbCertificadoDto, Integer> {
    List<TbCertificadoDto> findAll();
}