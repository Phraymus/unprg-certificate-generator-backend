package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbUsuarioDto;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;

import java.util.List;

public interface TbUsuarioService extends GenericCrud<TbUsuarioDto, Integer> {
    List<TbUsuarioDto> findAll();
}