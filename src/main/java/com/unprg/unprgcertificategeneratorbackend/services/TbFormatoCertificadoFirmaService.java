package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoFirmaIdDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbFormatoCertificadoFirmaService extends GenericCrud<TbFormatoCertificadoFirmaDto, TbFormatoCertificadoFirmaIdDto> {
    List<TbFormatoCertificadoFirmaDto> findAll();

    List<TbFormatoCertificadoFirmaDto> findAllByIdTbEventoCertificado(Integer idTbEvento);

}