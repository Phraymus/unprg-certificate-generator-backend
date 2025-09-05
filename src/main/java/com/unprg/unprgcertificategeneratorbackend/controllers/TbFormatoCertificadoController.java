package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbFormatoCertificadoService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tbformatocertificados")
@RequiredArgsConstructor
class TbFormatoCertificadoController extends GenericController<TbFormatoCertificadoDto, Integer> {

    private final TbFormatoCertificadoService tbFormatoCertificadoService;

    @Override
    public GenericCrud<TbFormatoCertificadoDto, Integer> getCrud() {
        return tbFormatoCertificadoService;
    }

    @Override
    protected Integer getPK(TbFormatoCertificadoDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbFormatoCertificadoDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Formato Certificado por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbFormatoCertificadoDto> list = tbFormatoCertificadoService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Formatos Certificado", list));
    }
}