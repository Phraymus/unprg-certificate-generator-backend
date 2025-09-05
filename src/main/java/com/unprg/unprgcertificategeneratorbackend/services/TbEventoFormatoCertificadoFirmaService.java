package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoFirmaIdDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbEventoFormatoCertificadoFirmaService extends GenericCrud<TbEventoFormatoCertificadoFirmaDto, TbEventoFormatoCertificadoFirmaIdDto> {
    List<TbEventoFormatoCertificadoFirmaDto> findAll();
}