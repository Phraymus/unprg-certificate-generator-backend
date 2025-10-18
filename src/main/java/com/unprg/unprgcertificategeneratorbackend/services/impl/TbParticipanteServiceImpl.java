package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteIdDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbPersonaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipante;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbParticipanteId;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbParticipanteRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoService;
import com.unprg.unprgcertificategeneratorbackend.services.TbParticipanteService;
import com.unprg.unprgcertificategeneratorbackend.services.TbPersonaService;
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
    private final TbPersonaService tbPersonaService;

    @Override
    public TbParticipanteDto insert(TbParticipanteDto tbParticipanteDto) {
        TbPersonaDto personaDto = tbPersonaService.findByDni(tbParticipanteDto.getTbPersona().getDni());
        if (personaDto == null) {
            personaDto= tbPersonaService.insert(tbParticipanteDto.getTbPersona());
        }
        tbParticipanteDto.setTbPersona(personaDto);
        TbParticipante entity = tbParticipanteDto.toEntity();
        entity.setId(TbParticipanteId.builder()
                .idtbEvento(entity.getIdtbEvento().getId())
                .idtbPersona(entity.getIdtbPersona().getId())
                .build());
        TbParticipante tbParticipante = tbParticipanteRepository.save(entity);
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
        TbParticipanteDto template = TbParticipanteDto.builder()
                .defTbEvento(new TbEventoDto())
                .defTbPersona(new TbPersonaDto())
                .build();
        Optional<TbParticipante> tbParticipante = tbParticipanteRepository.findById(id.toEntity());
        return tbParticipante.map(participante -> TbParticipanteDto.build().fromEntity(template, participante)).orElse(null);
    }

    @Override
    public List<TbParticipanteDto> findAll() {
        TbParticipanteDto template = TbParticipanteDto.builder()
                .defTbEvento(TbEventoDto.build())
                .defTbPersona(TbPersonaDto.build())
                .build();
        List<TbParticipante> tbParticipanteList = tbParticipanteRepository.findAll();
        return tbParticipanteList.stream().map(participante -> TbParticipanteDto.build().fromEntity(template, participante)).toList();
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