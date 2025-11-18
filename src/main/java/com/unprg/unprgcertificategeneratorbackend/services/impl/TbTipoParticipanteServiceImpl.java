package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbTipoParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbTipoParticipante;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbTipoParticipanteRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbTipoParticipanteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TbTipoParticipanteServiceImpl implements TbTipoParticipanteService {

    private final TbTipoParticipanteRepository tbTipoParticipanteRepository;

    @Override
    public TbTipoParticipanteDto insert(TbTipoParticipanteDto tbTipoParticipanteDto) {
        TbTipoParticipante tbTipoParticipante = tbTipoParticipanteRepository.save(tbTipoParticipanteDto.toEntity());
        return tbTipoParticipanteDto.fromEntity(tbTipoParticipante);
    }

    @Override
    public TbTipoParticipanteDto update(TbTipoParticipanteDto tbTipoParticipanteDto) {
        TbTipoParticipante tbTipoParticipante = tbTipoParticipanteRepository.save(tbTipoParticipanteDto.toEntity());
        return tbTipoParticipanteDto.fromEntity(tbTipoParticipante);
    }

    @Override
    public void delete(TbTipoParticipanteDto tbTipoParticipanteDto) {
        tbTipoParticipanteRepository.delete(tbTipoParticipanteDto.toEntity());
    }

    @Override
    public TbTipoParticipanteDto findById(Integer integer) {
        Optional<TbTipoParticipante> optional = tbTipoParticipanteRepository.findById(integer);
        return optional.map(tbTipoParticipante -> TbTipoParticipanteDto.build().fromEntity(tbTipoParticipante)).orElse(null);
    }

    @Override
    public List<TbTipoParticipanteDto> findAll() {
        List<TbTipoParticipante> tbTipoParticipantes = tbTipoParticipanteRepository.findAll();
        return tbTipoParticipantes.stream().map(tbTipoParticipante -> TbTipoParticipanteDto.build().fromEntity(tbTipoParticipante)).collect(Collectors.toList());
    }

    @Override
    public List<TbTipoParticipanteDto> findAllByEstado(Boolean estado) {
        List<TbTipoParticipante> tbTipoParticipantes = tbTipoParticipanteRepository.findAllByEstado(Boolean.TRUE.equals(estado) ? "1" : "0");
        return tbTipoParticipantes.stream().map(tbTipoParticipante -> TbTipoParticipanteDto.build().fromEntity(tbTipoParticipante)).collect(Collectors.toList());
    }
}
