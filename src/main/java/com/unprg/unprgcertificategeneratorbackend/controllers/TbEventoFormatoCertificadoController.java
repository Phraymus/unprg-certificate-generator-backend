package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoFormatoCertificadoService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbeventoformatocertificados")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbEventoFormatoCertificadoController extends GenericController<TbEventoFormatoCertificadoDto, Integer> {

    private final TbEventoFormatoCertificadoService tbEventoFormatoCertificadoService;

    @Override
    public GenericCrud<TbEventoFormatoCertificadoDto, Integer> getCrud() {
        return tbEventoFormatoCertificadoService;
    }

    @Override
    protected Integer getPK(TbEventoFormatoCertificadoDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbEventoFormatoCertificadoDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Evento Formato Certificado por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbEventoFormatoCertificadoDto> list = tbEventoFormatoCertificadoService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Eventos Formato Certificado", list));
    }

    @GetMapping(path = "/findByEventoId/{eventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findByEventoId(@PathVariable("eventoId") Integer eventoId) {
        TbEventoFormatoCertificadoDto dto = tbEventoFormatoCertificadoService.findById(eventoId);
        return ResponseEntity.ok(ApiResponse.ok("obtener Evento Formato Certificado por ID de evento", dto));
    }
}