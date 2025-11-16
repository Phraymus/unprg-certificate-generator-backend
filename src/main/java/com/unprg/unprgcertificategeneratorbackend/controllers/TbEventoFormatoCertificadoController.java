package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoIdDto;
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
class TbEventoFormatoCertificadoController extends GenericController<TbEventoFormatoCertificadoDto, TbEventoFormatoCertificadoIdDto> {

    private final TbEventoFormatoCertificadoService tbEventoFormatoCertificadoService;

    @Override
    public GenericCrud<TbEventoFormatoCertificadoDto, TbEventoFormatoCertificadoIdDto> getCrud() {
        return tbEventoFormatoCertificadoService;
    }

    @Override
    protected TbEventoFormatoCertificadoIdDto getPK(TbEventoFormatoCertificadoDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{idEvento}/{idFormatoCertificado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("idEvento") Integer idEvento, @PathVariable("idFormatoCertificado") Integer idFormatoCertificado) {
        TbEventoFormatoCertificadoDto dto = getCrud().findById(
                TbEventoFormatoCertificadoIdDto.builder()
                        .idtbEvento(idEvento)
                        .idtbFormatoCertificado(idFormatoCertificado)
                        .build()
        );
        return ResponseEntity.ok(ApiResponse.ok("obtener Evento Formato Certificado por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbEventoFormatoCertificadoDto> list = tbEventoFormatoCertificadoService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Eventos Formato Certificado", list));
    }

//    @GetMapping(path = "/findByEventoId/{eventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ApiResponse> findByEventoId(@PathVariable("eventoId") Integer eventoId) {
//        TbEventoFormatoCertificadoDto dto = tbEventoFormatoCertificadoService.findById(eventoId);
//        return ResponseEntity.ok(ApiResponse.ok("obtener Evento Formato Certificado por ID de evento", dto));
//    }
}