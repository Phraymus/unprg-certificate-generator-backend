package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEvento;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbEventoRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbEventoServiceImpl implements TbEventoService {

    private final TbEventoRepository tbEventoRepository;

    @Override
    public TbEventoDto insert(TbEventoDto tbEventoDto) {
        TbEvento tbEvento = tbEventoRepository.save(tbEventoDto.toEntity());
        return tbEventoDto.fromEntity(tbEvento);
    }

    @Override
    public TbEventoDto update(TbEventoDto tbEventoDto) {
        TbEvento tbEvento = tbEventoRepository.save(tbEventoDto.toEntity());
        return tbEventoDto.fromEntity(tbEvento);
    }

    @Override
    public void delete(TbEventoDto tbEventoDto) {
        tbEventoRepository.delete(tbEventoDto.toEntity());
    }

    @Override
    public TbEventoDto findById(Integer integer) {
        Optional<TbEvento> tbEvento = tbEventoRepository.findById(integer);
        return tbEvento.map(evento -> TbEventoDto.build().fromEntity(evento)).orElse(null);
    }

    @Override
    public List<TbEventoDto> findAll() {
        List<TbEvento> tbEventoList = tbEventoRepository.findAll();
        return tbEventoList.stream().map(evento -> TbEventoDto.build().fromEntity(evento)).toList();
    }
}