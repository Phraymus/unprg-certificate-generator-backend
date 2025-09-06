package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoFirmaIdDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoFormatoCertificadoFirmaService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbeventoformatocertificadofirmas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbEventoFormatoCertificadoFirmaController extends GenericController<TbEventoFormatoCertificadoFirmaDto, TbEventoFormatoCertificadoFirmaIdDto> {

    private final TbEventoFormatoCertificadoFirmaService tbEventoFormatoCertificadoFirmaService;

    @Override
    public GenericCrud<TbEventoFormatoCertificadoFirmaDto, TbEventoFormatoCertificadoFirmaIdDto> getCrud() {
        return tbEventoFormatoCertificadoFirmaService;
    }

    @Override
    protected TbEventoFormatoCertificadoFirmaIdDto getPK(TbEventoFormatoCertificadoFirmaDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{idFirma}/{idEventoFormato}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("idFirma") Integer idFirma, 
                                               @PathVariable("idEventoFormato") Integer idEventoFormato) {
        TbEventoFormatoCertificadoFirmaIdDto id = TbEventoFormatoCertificadoFirmaIdDto.builder()
                .idtbFirma(idFirma)
                .tbEventoFormatoCertificadoIdtbEvento(idEventoFormato)
                .build();
        TbEventoFormatoCertificadoFirmaDto dto = getCrud().findById(id);
        return ResponseEntity.ok(ApiResponse.ok("obtener Evento Formato Certificado Firma por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbEventoFormatoCertificadoFirmaDto> list = tbEventoFormatoCertificadoFirmaService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Eventos Formato Certificado Firmas", list));
    }
}