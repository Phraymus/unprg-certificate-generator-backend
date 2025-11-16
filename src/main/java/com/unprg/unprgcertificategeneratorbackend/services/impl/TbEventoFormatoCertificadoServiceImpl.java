package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.*;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificado;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbEventoFormatoCertificadoRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoFormatoCertificadoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbEventoFormatoCertificadoServiceImpl implements TbEventoFormatoCertificadoService {

    private final TbEventoFormatoCertificadoRepository tbEventoFormatoCertificadoRepository;

    @Override
    public TbEventoFormatoCertificadoDto insert(TbEventoFormatoCertificadoDto tbEventoFormatoCertificadoDto) {
        TbEventoFormatoCertificado tbEventoFormatoCertificado = tbEventoFormatoCertificadoRepository.save(tbEventoFormatoCertificadoDto.toEntity());
        return tbEventoFormatoCertificadoDto.fromEntity(tbEventoFormatoCertificado);
    }

    @Override
    public TbEventoFormatoCertificadoDto update(TbEventoFormatoCertificadoDto tbEventoFormatoCertificadoDto) {
        TbEventoFormatoCertificado tbEventoFormatoCertificado = tbEventoFormatoCertificadoRepository.save(tbEventoFormatoCertificadoDto.toEntity());
        return tbEventoFormatoCertificadoDto.fromEntity(tbEventoFormatoCertificado);
    }

    @Override
    public void delete(TbEventoFormatoCertificadoDto tbEventoFormatoCertificadoDto) {
        tbEventoFormatoCertificadoRepository.delete(tbEventoFormatoCertificadoDto.toEntity());
    }

    @Override
    public TbEventoFormatoCertificadoDto findById(TbEventoFormatoCertificadoIdDto id) {
        TbEventoFormatoCertificadoDto template = TbEventoFormatoCertificadoDto.builder()
                .defTbEvento(new TbEventoDto())
                .defTbFormatoCertificado(new TbFormatoCertificadoDto())
                .defTbTipoParticipante(new TbTipoParticipanteDto())
                .build();
        Optional<TbEventoFormatoCertificado> tbEventoFormatoCertificado = tbEventoFormatoCertificadoRepository.findById(id.toEntity());
        return tbEventoFormatoCertificado.map(eventoFormatoCertificado -> TbEventoFormatoCertificadoDto.build().fromEntity(template, eventoFormatoCertificado)).orElse(null);
    }

    @Override
    public List<TbEventoFormatoCertificadoDto> findAll() {
        TbEventoFormatoCertificadoDto template = TbEventoFormatoCertificadoDto.builder()
                .defTbEvento(new TbEventoDto())
                .defTbFormatoCertificado(new TbFormatoCertificadoDto())
                .defTbTipoParticipante(new TbTipoParticipanteDto())
                .build();
        List<TbEventoFormatoCertificado> tbEventoFormatoCertificadoList = tbEventoFormatoCertificadoRepository.findAll();
        return tbEventoFormatoCertificadoList.stream().map(eventoFormatoCertificado -> TbEventoFormatoCertificadoDto.build().fromEntity(template, eventoFormatoCertificado)).toList();
    }
}