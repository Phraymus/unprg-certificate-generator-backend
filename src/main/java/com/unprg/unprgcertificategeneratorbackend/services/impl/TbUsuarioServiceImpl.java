package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbUsuarioDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbUsuario;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbUsuarioRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbUsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbUsuarioServiceImpl implements TbUsuarioService {

    private final TbUsuarioRepository tbUsuarioRepository;

    @Override
    public TbUsuarioDto insert(TbUsuarioDto tbUsuarioDto) {
        TbUsuario tbUsuario = tbUsuarioRepository.save(tbUsuarioDto.toEntity());
        return tbUsuarioDto.fromEntity(tbUsuario);
    }

    @Override
    public TbUsuarioDto update(TbUsuarioDto tbUsuarioDto) {
        TbUsuario tbUsuario = tbUsuarioRepository.save(tbUsuarioDto.toEntity());
        return tbUsuarioDto.fromEntity(tbUsuario);
    }

    @Override
    public void delete(TbUsuarioDto tbUsuarioDto) {
        tbUsuarioRepository.delete(tbUsuarioDto.toEntity());
    }

    @Override
    public TbUsuarioDto findById(Integer integer) {
        Optional<TbUsuario> tbUsuario = tbUsuarioRepository.findById(integer);
        return tbUsuario.map(usuario -> TbUsuarioDto.build().fromEntity(usuario)).orElse(null);
    }

    @Override
    public List<TbUsuarioDto> findAll() {
        List<TbUsuario> tbUsuarioList = tbUsuarioRepository.findAll();
        return tbUsuarioList.stream().map(usuario -> TbUsuarioDto.build().fromEntity(usuario)).toList();
    }
}