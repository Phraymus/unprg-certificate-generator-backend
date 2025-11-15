package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.*;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificadoFirma;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbFormatoCertificadoFirmaRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbFormatoCertificadoFirmaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbFormatoCertificadoFirmaServiceImpl implements TbFormatoCertificadoFirmaService {

    private final TbFormatoCertificadoFirmaRepository tbFormatoCertificadoFirmaRepository;

    @Override
    public TbFormatoCertificadoFirmaDto insert(TbFormatoCertificadoFirmaDto tbFormatoCertificadoFirmaDto) {
//        tbEventoFormatoCertificadoFirmaRepository.deleteAllByTbEventoFormatoCertificadoIdtbEvento(tbEventoFormatoCertificadoFirmaDto.getTbEventoFormatoCertificadoIdtbEvento().toEntity());
        TbFormatoCertificadoFirma tbFormatoCertificadoFirma = tbFormatoCertificadoFirmaRepository.save(tbFormatoCertificadoFirmaDto.toEntity());
        return tbFormatoCertificadoFirmaDto.fromEntity(tbFormatoCertificadoFirma);
    }

    @Override
    public TbFormatoCertificadoFirmaDto update(TbFormatoCertificadoFirmaDto tbFormatoCertificadoFirmaDto) {
        TbFormatoCertificadoFirma tbFormatoCertificadoFirma = tbFormatoCertificadoFirmaRepository.save(tbFormatoCertificadoFirmaDto.toEntity());
        return tbFormatoCertificadoFirmaDto.fromEntity(tbFormatoCertificadoFirma);
    }

    @Override
    public void delete(TbFormatoCertificadoFirmaDto tbFormatoCertificadoFirmaDto) {
        tbFormatoCertificadoFirmaRepository.delete(tbFormatoCertificadoFirmaDto.toEntity());
    }

    @Override
    public TbFormatoCertificadoFirmaDto findById(TbFormatoCertificadoFirmaIdDto id) {
        Optional<TbFormatoCertificadoFirma> tbEventoFormatoCertificadoFirma = tbFormatoCertificadoFirmaRepository.findById(id.toEntity());
        return tbEventoFormatoCertificadoFirma.map(eventoFormatoCertificadoFirma -> TbFormatoCertificadoFirmaDto.build().fromEntity(eventoFormatoCertificadoFirma)).orElse(null);
    }

    @Override
    public List<TbFormatoCertificadoFirmaDto> findAll() {
        List<TbFormatoCertificadoFirma> tbFormatoCertificadoFirmaList = tbFormatoCertificadoFirmaRepository.findAll();
        return tbFormatoCertificadoFirmaList.stream().map(eventoFormatoCertificadoFirma -> TbFormatoCertificadoFirmaDto.build().fromEntity(eventoFormatoCertificadoFirma)).toList();
    }

    @Override
    public List<TbFormatoCertificadoFirmaDto> findAllByIdTbEventoCertificado(Integer idTbEventoFormatoCertificado) {
        TbFormatoCertificadoFirmaDto template = TbFormatoCertificadoFirmaDto.builder()
                .defTbFormatoCertificado(new TbFormatoCertificadoDto())
                .defTbFirma(new TbFirmaDto())
                .build();
//        List<TbFormatoCertificadoFirma> tbFormatoCertificadoFirmaList = tbEventoFormatoCertificadoFirmaRepository.findAllByTbEventoFormatoCertificadoIdtbEventoId(idTbEventoFormatoCertificado);
//        return tbFormatoCertificadoFirmaList.stream().map(eventoFormatoCertificadoFirma -> TbFormatoCertificadoFirmaDto.build().fromEntity(template, eventoFormatoCertificadoFirma)).toList();
        return Collections.emptyList();
    }
}