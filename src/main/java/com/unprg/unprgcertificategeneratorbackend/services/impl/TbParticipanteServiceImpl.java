package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteIdDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbPersonaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEvento;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipante;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbPersona;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbParticipanteRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbParticipanteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbParticipanteServiceImpl implements TbParticipanteService {

    private final TbParticipanteRepository tbParticipanteRepository;

    @Override
    public TbParticipanteDto insert(TbParticipanteDto tbParticipanteDto) {
        TbParticipante tbParticipante = tbParticipanteRepository.save(tbParticipanteDto.toEntity());
        return tbParticipanteDto.fromEntity(tbParticipante);
    }

    @Override
    public TbParticipanteDto update(TbParticipanteDto tbParticipanteDto) {
        TbParticipante tbParticipante = tbParticipanteRepository.save(tbParticipanteDto.toEntity());
        return tbParticipanteDto.fromEntity(tbParticipante);
    }

    @Override
    public void delete(TbParticipanteDto tbParticipanteDto) {
        tbParticipanteRepository.delete(tbParticipanteDto.toEntity());
    }

    @Override
    public TbParticipanteDto findById(TbParticipanteIdDto id) {
        Optional<TbParticipante> tbParticipante = tbParticipanteRepository.findById(id.toEntity());
        return tbParticipante.map(participante -> TbParticipanteDto.build().fromEntity(participante)).orElse(null);
    }

    @Override
    public List<TbParticipanteDto> findAll() {
        List<TbParticipante> tbParticipanteList = tbParticipanteRepository.findAll();
        return tbParticipanteList.stream().map(participante -> TbParticipanteDto.build().fromEntity(participante)).toList();
    }

    @Override
    public List<TbParticipanteDto> findAllByIdEvento(Integer k) {
        TbParticipanteDto template = TbParticipanteDto.builder()
                .defTbEvento(TbEventoDto.build())
                .defTbPersona(TbPersonaDto.build())
                .build();
        List<TbParticipante> tbParticipanteList = tbParticipanteRepository.findAllByIdtbEventoId(k);
        return tbParticipanteList.stream().map(template::fromEntity).toList();
    }
}