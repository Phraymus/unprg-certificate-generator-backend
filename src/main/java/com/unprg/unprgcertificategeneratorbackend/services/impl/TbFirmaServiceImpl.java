package com.unprg.unprgcertificategeneratorbackend.services.impl;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFirma;
import com.unprg.unprgcertificategeneratorbackend.repositories.TbFirmaRepository;
import com.unprg.unprgcertificategeneratorbackend.services.TbFirmaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TbFirmaServiceImpl implements TbFirmaService {

    private final TbFirmaRepository tbFirmaRepository;

    @Override
    public TbFirmaDto insert(TbFirmaDto tbFirmaDto) {
        TbFirma tbFirma = tbFirmaRepository.save(tbFirmaDto.toEntity());
        return tbFirmaDto.fromEntity(tbFirma);
    }

    @Override
    public TbFirmaDto update(TbFirmaDto tbFirmaDto) {
        TbFirma tbFirma = tbFirmaRepository.save(tbFirmaDto.toEntity());
        return tbFirmaDto.fromEntity(tbFirma);
    }

    @Override
    public void delete(TbFirmaDto tbFirmaDto) {
        tbFirmaRepository.delete(tbFirmaDto.toEntity());
    }

    @Override
    public TbFirmaDto findById(Integer integer) {
        Optional<TbFirma> tbFirma = tbFirmaRepository.findById(integer);
        return tbFirma.map(firma -> TbFirmaDto.build().fromEntity(firma)).orElse(null);
    }

    @Override
    public List<TbFirmaDto> findAll() {
        List<TbFirma> tbFirmaList = tbFirmaRepository.findAll();
        return tbFirmaList.stream().map(firma -> TbFirmaDto.build().fromEntity(firma)).toList();
    }
}
