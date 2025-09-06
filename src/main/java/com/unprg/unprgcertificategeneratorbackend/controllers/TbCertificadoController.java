package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbCertificadoService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbcertificados")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbCertificadoController extends GenericController<TbCertificadoDto, Integer> {

    private final TbCertificadoService tbCertificadoService;

    @Override
    public GenericCrud<TbCertificadoDto, Integer> getCrud() {
        return tbCertificadoService;
    }

    @Override
    protected Integer getPK(TbCertificadoDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbCertificadoDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Certificado por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbCertificadoDto> list = tbCertificadoService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Certificados", list));
    }
}