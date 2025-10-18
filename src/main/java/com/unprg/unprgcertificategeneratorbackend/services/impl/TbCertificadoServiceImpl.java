package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbCertificado;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbCertificadoRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbCertificadoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbCertificadoServiceImpl implements TbCertificadoService {

    private final TbCertificadoRepository tbCertificadoRepository;

    @Override
    public TbCertificadoDto insert(TbCertificadoDto tbCertificadoDto) {
        TbCertificado tbCertificado = tbCertificadoRepository.save(tbCertificadoDto.toEntity());
        return tbCertificadoDto.fromEntity(tbCertificado);
    }

    @Override
    public TbCertificadoDto update(TbCertificadoDto tbCertificadoDto) {
        TbCertificado tbCertificado = tbCertificadoRepository.save(tbCertificadoDto.toEntity());
        return tbCertificadoDto.fromEntity(tbCertificado);
    }

    @Override
    public void delete(TbCertificadoDto tbCertificadoDto) {
        tbCertificadoRepository.delete(tbCertificadoDto.toEntity());
    }

    @Override
    public TbCertificadoDto findById(Integer integer) {
        Optional<TbCertificado> tbCertificado = tbCertificadoRepository.findById(integer);
        return tbCertificado.map(certificado -> TbCertificadoDto.build().fromEntity(certificado)).orElse(null);
    }

    @Override
    public List<TbCertificadoDto> findAll() {
        List<TbCertificado> tbCertificadoList = tbCertificadoRepository.findAll();
        return tbCertificadoList.stream().map(certificado -> TbCertificadoDto.build().fromEntity(certificado)).toList();
    }
}