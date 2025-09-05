package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificado;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbFormatoCertificadoRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbFormatoCertificadoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbFormatoCertificadoServiceImpl implements TbFormatoCertificadoService {

    private final TbFormatoCertificadoRepository tbFormatoCertificadoRepository;

    @Override
    public TbFormatoCertificadoDto insert(TbFormatoCertificadoDto tbFormatoCertificadoDto) {
        TbFormatoCertificado tbFormatoCertificado = tbFormatoCertificadoRepository.save(tbFormatoCertificadoDto.toEntity());
        return tbFormatoCertificadoDto.fromEntity(tbFormatoCertificado);
    }

    @Override
    public TbFormatoCertificadoDto update(TbFormatoCertificadoDto tbFormatoCertificadoDto) {
        TbFormatoCertificado tbFormatoCertificado = tbFormatoCertificadoRepository.save(tbFormatoCertificadoDto.toEntity());
        return tbFormatoCertificadoDto.fromEntity(tbFormatoCertificado);
    }

    @Override
    public void delete(TbFormatoCertificadoDto tbFormatoCertificadoDto) {
        tbFormatoCertificadoRepository.delete(tbFormatoCertificadoDto.toEntity());
    }

    @Override
    public TbFormatoCertificadoDto findById(Integer integer) {
        Optional<TbFormatoCertificado> tbFormatoCertificado = tbFormatoCertificadoRepository.findById(integer);
        return tbFormatoCertificado.map(formatoCertificado -> TbFormatoCertificadoDto.build().fromEntity(formatoCertificado)).orElse(null);
    }

    @Override
    public List<TbFormatoCertificadoDto> findAll() {
        List<TbFormatoCertificado> tbFormatoCertificadoList = tbFormatoCertificadoRepository.findAll();
        return tbFormatoCertificadoList.stream().map(formatoCertificado -> TbFormatoCertificadoDto.build().fromEntity(formatoCertificado)).toList();
    }
}