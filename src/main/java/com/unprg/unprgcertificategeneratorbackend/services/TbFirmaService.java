package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbFirmaService extends GenericCrud<TbFirmaDto, Integer> {
    List<TbFirmaDto> findAll();
}
