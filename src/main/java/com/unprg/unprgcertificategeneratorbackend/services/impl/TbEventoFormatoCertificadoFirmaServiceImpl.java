package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoFirmaIdDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEventoFormatoCertificadoFirma;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbEventoFormatoCertificadoFirmaRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoFormatoCertificadoFirmaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbEventoFormatoCertificadoFirmaServiceImpl implements TbEventoFormatoCertificadoFirmaService {

    private final TbEventoFormatoCertificadoFirmaRepository tbEventoFormatoCertificadoFirmaRepository;

    @Override
    public TbEventoFormatoCertificadoFirmaDto insert(TbEventoFormatoCertificadoFirmaDto tbEventoFormatoCertificadoFirmaDto) {
//        tbEventoFormatoCertificadoFirmaRepository.deleteAllByTbEventoFormatoCertificadoIdtbEvento(tbEventoFormatoCertificadoFirmaDto.getTbEventoFormatoCertificadoIdtbEvento().toEntity());
        TbEventoFormatoCertificadoFirma tbEventoFormatoCertificadoFirma = tbEventoFormatoCertificadoFirmaRepository.save(tbEventoFormatoCertificadoFirmaDto.toEntity());
        return tbEventoFormatoCertificadoFirmaDto.fromEntity(tbEventoFormatoCertificadoFirma);
    }

    @Override
    public TbEventoFormatoCertificadoFirmaDto update(TbEventoFormatoCertificadoFirmaDto tbEventoFormatoCertificadoFirmaDto) {
        TbEventoFormatoCertificadoFirma tbEventoFormatoCertificadoFirma = tbEventoFormatoCertificadoFirmaRepository.save(tbEventoFormatoCertificadoFirmaDto.toEntity());
        return tbEventoFormatoCertificadoFirmaDto.fromEntity(tbEventoFormatoCertificadoFirma);
    }

    @Override
    public void delete(TbEventoFormatoCertificadoFirmaDto tbEventoFormatoCertificadoFirmaDto) {
        tbEventoFormatoCertificadoFirmaRepository.delete(tbEventoFormatoCertificadoFirmaDto.toEntity());
    }

    @Override
    public TbEventoFormatoCertificadoFirmaDto findById(TbEventoFormatoCertificadoFirmaIdDto id) {
        Optional<TbEventoFormatoCertificadoFirma> tbEventoFormatoCertificadoFirma = tbEventoFormatoCertificadoFirmaRepository.findById(id.toEntity());
        return tbEventoFormatoCertificadoFirma.map(eventoFormatoCertificadoFirma -> TbEventoFormatoCertificadoFirmaDto.build().fromEntity(eventoFormatoCertificadoFirma)).orElse(null);
    }

    @Override
    public List<TbEventoFormatoCertificadoFirmaDto> findAll() {
        List<TbEventoFormatoCertificadoFirma> tbEventoFormatoCertificadoFirmaList = tbEventoFormatoCertificadoFirmaRepository.findAll();
        return tbEventoFormatoCertificadoFirmaList.stream().map(eventoFormatoCertificadoFirma -> TbEventoFormatoCertificadoFirmaDto.build().fromEntity(eventoFormatoCertificadoFirma)).toList();
    }

    @Override
    public List<TbEventoFormatoCertificadoFirmaDto> findAllByIdTbEventoCertificado(Integer idTbEventoFormatoCertificado) {
        TbEventoFormatoCertificadoFirmaDto template = TbEventoFormatoCertificadoFirmaDto.builder()
                .defTbEventoFormatoCertificadoIdtbEvento(new TbEventoFormatoCertificadoDto())
                .defIdtbFirma(new TbFirmaDto())
                .build();
        List<TbEventoFormatoCertificadoFirma> tbEventoFormatoCertificadoFirmaList = tbEventoFormatoCertificadoFirmaRepository.findAllByTbEventoFormatoCertificadoIdtbEventoId(idTbEventoFormatoCertificado);
        return tbEventoFormatoCertificadoFirmaList.stream().map(eventoFormatoCertificadoFirma -> TbEventoFormatoCertificadoFirmaDto.build().fromEntity(template, eventoFormatoCertificadoFirma)).toList();
    }
}