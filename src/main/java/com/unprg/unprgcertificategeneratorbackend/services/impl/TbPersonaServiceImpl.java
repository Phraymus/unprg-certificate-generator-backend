package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbPersonaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbPersona;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbPersonaRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbPersonaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbPersonaServiceImpl implements TbPersonaService {

    private final TbPersonaRepository tbPersonaRepository;


    @Override
    public TbPersonaDto insert(TbPersonaDto tbPersonaDto) {
        TbPersona tbPersona = tbPersonaRepository.save(tbPersonaDto.toEntity());
        return tbPersonaDto.fromEntity(tbPersona);
    }

    @Override
    public TbPersonaDto update(TbPersonaDto tbPersonaDto) {
        TbPersona tbPersona = tbPersonaRepository.save(tbPersonaDto.toEntity());
        return tbPersonaDto.fromEntity(tbPersona);
    }

    @Override
    public void delete(TbPersonaDto tbPersonaDto) {
        tbPersonaRepository.delete(tbPersonaDto.toEntity());
    }

    @Override
    public TbPersonaDto findById(Integer integer) {
        Optional<TbPersona> tbPersona = tbPersonaRepository.findById(integer);
        return tbPersona.map(persona -> TbPersonaDto.build().fromEntity(persona)).orElse(null);
    }

    @Override
    public TbPersonaDto findByDni(String dni) {
        Optional<TbPersona> tbPersona = tbPersonaRepository.findByDni(dni);
        return tbPersona.map(persona -> TbPersonaDto.build().fromEntity(persona)).orElse(null);
    }

    @Override
    public List<TbPersonaDto> findAll() {
        List<TbPersona> tbPersonaList = tbPersonaRepository.findAll();
        return tbPersonaList.stream().map(persona -> TbPersonaDto.build().fromEntity(persona)).toList();
    }

    @Override
    public List<TbPersonaDto> findAllByNombreOrDni(String search) {
        List<TbPersona> tbPersonaList = tbPersonaRepository.findAllByNombresOrApellidoPaternoOrApellidoMaternoOrDniLike(search);
        return tbPersonaList.stream().map(persona -> TbPersonaDto.build().fromEntity(persona)).toList();
    }
}